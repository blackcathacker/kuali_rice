/*
 * Copyright 2005-2007 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.core.datadictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rules;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.core.datadictionary.exception.CompletionException;
import org.kuali.core.datadictionary.exception.DuplicateEntryException;
import org.kuali.core.datadictionary.exception.InitException;
import org.kuali.core.datadictionary.exception.ParseException;
import org.kuali.core.datadictionary.exception.SourceException;
import org.kuali.core.service.KualiConfigurationService;
import org.kuali.core.service.KualiGroupService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import edu.iu.uis.eden.util.ClassLoaderUtils;
import edu.iu.uis.eden.xml.ClassLoaderEntityResolver;

/**
 * Assembles a DataDictionary from the contents of one or more specifed XML
 * files or directories containing XML files.
 * 
 * 
 */
public class DataDictionaryBuilder {
	// logger
	private static Log LOG = LogFactory.getLog(DataDictionaryBuilder.class);

	protected static final String PACKAGE_PREFIX = "/org/kuali/core/datadictionary/";

	// DTD registration info
	protected final static String[][] DTD_REGISTRATION_INFO = { { "-//Kuali Project//DTD Data Dictionary 1.0//EN", PACKAGE_PREFIX + "dataDictionary-1_0.dtd" }, };

	private KualiConfigurationService kualiConfigurationService;

	private KualiGroupService kualiGroupService;

	protected DataDictionary dataDictionary;

	private boolean exceptionsOccurred;

	/**
	 * Default constructor
	 */
	public DataDictionaryBuilder(ValidationCompletionUtils validationCompletionUtils) {
		LOG.info("created DataDictionaryBuilder");

		dataDictionary = new DataDictionary(validationCompletionUtils);
		setExceptionsOccurred(false);
	}

	/**
	 * @return the current dataDictionary
	 * @throws IllegalStateException
	 *             if any exceptions occurred during calls to add*Entries
	 *             constructing the dataDictionary
	 */
	public DataDictionary getDataDictionary() {
		if (getExceptionsOccurred()) {
			throw new IllegalStateException("illegal attempt to retrieve invalid DataDictionary");
		}

		return this.dataDictionary;
	}

	/**
	 * Given the name of an XML file, or of a directory containing XML files,
	 * adds the entries defined in the XML file or files to the DataDictionary
	 * being built. Duplicate class entries (antries using a classname already
	 * in use) will result in a DuplicateEntryException being added to the
	 * cumulative entryExceptions list. If sourceMustExist is true, a
	 * nonexistent source will result in a SourceException being thrown.
	 * 
	 * @param sourceName
	 *            XML file or directory containing XML files to be added
	 * @param sourceMustExist
	 *            throw a SourceException if the given source cannot be found
	 * @throws IllegalArgumentException
	 *             if the given sourceName is blank
	 * @throws DuplicateEntryException
	 *             if one of the files being added contains an entry using a
	 *             classname for which an entrry has already been defined
	 * @throws ParseException
	 *             if an error occurs processing an entry file
	 * @throws SourceException
	 *             if sourceMustExist is true and the source can't be found
	 */
	public void addUniqueEntries(String sourceName, boolean sourceMustExist) {
		addEntriesWrapper(sourceName, sourceMustExist, false);
	}

	/**
	 * Given the name of an XML file, or of a directory containing XML files,
	 * adds the entries defined in the XML file or files to the DataDictionary
	 * being built. Duplicate class entries will override earlier class entries.
	 * 
	 * @param sourceName
	 *            XML file or directory containing XML files to be added
	 * @param sourceMustExist
	 *            throw a SourceException if the given source cannot be found
	 * @throws IllegalArgumentException
	 *             if the given sourceName is blank
	 * @throws ParseException
	 *             if an error occurs processing an entry file
	 * @throws SourceException
	 *             if sourceMustExist is true and the source can't be found
	 */
	public void addOverrideEntries(String sourceName, boolean sourceMustExist) {
		addEntriesWrapper(sourceName, sourceMustExist, true);
	}

	/**
	 * Asks the current dataDictionary to perform whatever post-construction
	 * cleanup it needs to do before being ready for public consumption.
	 * 
	 * @throws CompletionException
	 *             if the dataDictionry runs into problems
	 */
	public void completeInitialization() {
		LOG.info("completing dataDictionary initialization");
		dataDictionary.completeInitialization(getKualiGroupService(), getKualiConfigurationService());
		LOG.info("completed dataDictionary initialization");
	}

	/**
	 * Wraps addEntries with a try-catch block which prevents SourceExceptions
	 * from escaping if sourceMustExist is false.
	 * 
	 * @throws SourceException
	 *             if the given source does not exist, and sourceMustExist is
	 *             true
	 */
	protected void addEntriesWrapper(String sourceName, boolean sourceMustExist, boolean allowOverrides) {
		LOG.debug("adding dataDictionary entries from source '" + sourceName + "'");
		try {
			addEntries(sourceName, allowOverrides);
		} catch (SourceException e) {
			if (sourceMustExist) {
				throw e;
			}
		}
		LOG.debug("added dataDictionary entries from source '" + sourceName + "'");
	}

	private ThreadLocal<Rules> digesterRules = new ThreadLocal<Rules>();

	/**
	 * Parses each XML file on the given list, adding the class entry or entries
	 * defined there to the current DataDictionary. If allowOverrides is true,
	 * treats duplicate entries (second and subsequent entry using a given
	 * classname) as an error; otherwise, the last entry processed using a given
	 * classname will replace earlier entries.
	 * 
	 * @param sourceName
	 *            name of XML file or directory containing XML files
	 * @param sourceMustExist
	 *            throw a SourceException if the given source cannot be found
	 * @param allowOverrides
	 * @throws ParseException
	 *             if an error occurs when processing the given list of xmlFiles
	 * @throws DuplicateEntryException
	 *             if allowOverrides is false, and an entry is defined using a
	 *             classname for which an entry already exists
	 */
	protected synchronized void addEntries(String sourceName, boolean allowOverrides) {
		if (StringUtils.isEmpty(sourceName)) {
			throw new IllegalArgumentException("invalid (empty) sourceName");
		}

		// ensure a separate copy of the digester rules per accessing thread
		if (digesterRules.get() == null) {
			LOG.debug("addEntries(): Loading Digester Rules");
			digesterRules.set(loadRules());
		}
		String logName = null;
		Digester digester = buildDigester(digesterRules.get());
		// try {

		dataDictionary.setAllowOverrides(allowOverrides);

		try {
			listSourceFiles(sourceName, digester);
		} catch (Exception e) {
			if (e instanceof ParseException) {
				throw new ParseException("Problems parsing DD", e);	
			}
			throw (ParseException)e;
		}

		clearCurrentDigester();
		clearCurrentFilename();
		//			
		// // setExceptionsOccurred(true);
		//			
		// // for (Map.Entry<String, InputStream> xmlFile : xmlFiles.entrySet())
		// {
		//				
		//			
		// // for (Iterator i = xmlFiles.iterator(); i.hasNext();) {
		// // File xmlFile = (File) i.next();
		// // logName = xmlFile.getAbsolutePath();
		// boolean success = false;
		// int tryCount = 0;
		// // to handle exceptions caused by multi-threaded loading of the
		// // DD
		// // the Struts ActionServlet classes re-initialzes a static
		// // variable
		// // that removes the property converters (String -> int, etc...)
		// //
		// // if something calls
		// // org.apache.commons.beanutils.ConvertUtils.deregister() while
		// // the digester is parsing a file, a method within
		// // ConvertUtilsBean throws an NPE when
		// // it tries to convert a string from the XML file into the
		// // appropriate type for the
		// // cooresponding bean property. The deregister method
		// // immediately puts the missing
		// // converter back, but there are a few milliseconds during which
		// // this problem can occur.
		// //
		// // The loop below attempts to handle this problem by allowing
		// // the digester to retry a file once if
		// // an NPE is thrown during digester parsing.
		// while (!success && tryCount < 2) {
		// try {
		// tryCount++;
		// digester.setErrorHandler(new XmlErrorHandler(xmlFile.getKey()));
		// setCurrentDigester(digester);
		// setCurrentFilename(xmlFile.getKey());
		// digester.push(dataDictionary);
		// // digester.setEntityResolver(new ClassLoaderEntityResolver());
		//						
		// success = true;
		// } catch (SAXException ex) {
		// if (tryCount < 2 && ex.getException() instanceof
		// NullPointerException) {
		// // suppress
		// LOG.warn("Digester parsing error probably related to concurrency",
		// ex.getCause());
		// digester.clear();
		// } else {
		// throw ex;
		// }
		// } finally {
		// clearCurrentDigester();
		// clearCurrentFilename();
		// if (xmlFile.getValue() != null) {
		// xmlFile.getValue().close();
		// }
		// }
		// }
		// }
		// setExceptionsOccurred(false);
		// } catch (IOException e) {
		// throw new SourceException("unable to load XML file '" + logName +
		// "'", e);
		// } catch (DuplicateEntryException e) {
		// throw new DuplicateEntryException("duplicate entry parsing XML file
		// '" + logName + "'", e);
		// } catch (SAXException e) {
		// Exception saxCause = e.getException();
		// if (saxCause instanceof DuplicateEntryException) {
		// DuplicateEntryException dee = (DuplicateEntryException) saxCause;
		// String newMessage = "unable to parse XML file '" + logName + "': " +
		// saxCause.getMessage();
		//
		// throw new DuplicateEntryException(newMessage, saxCause.getCause());
		// } else if (saxCause instanceof DataDictionaryException) {
		// throw (DataDictionaryException) saxCause;
		// } else {
		// LOG.fatal("unable to parse XML file '" + logName + "'", saxCause);
		// throw new ParseException("unable to parse XML file '" + logName +
		// "'", e);
		// }
		// } finally {
		// if (digester != null) {
		// digester.clear();
		// }

		// }
	}

	/**
	 * @param source
	 *            XML file, or package containing XML files (which, if a
	 *            package, must end with the ".xml" extension)
	 * @return List of XML Files located using the given sourceName
	 * @throws IOException
	 * @throws IOException
	 *             if there's a problem locating the named source
	 */
	protected void listSourceFiles(String sourceName, Digester digester) throws Exception {
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader(ClassLoaderUtils.getDefaultClassLoader());
		Resource resource = resourceLoader.getResource(sourceName);

		if (sourceName.indexOf(".xml") < 0) {
			if (resource.exists()) {
				listSources(resource.getFile(), digester);
			} else {
				LOG.debug("Could not find " + sourceName);
			}
		} else {
			InputStream is = resource.getInputStream();
			if (is == null) {
				throw new ParseException("Cannot find file: " + sourceName);
			}
			LOG.debug("Adding file " + resource.getFilename() + " to DD.");
			digest(is, sourceName, digester);
		}
	}

	protected void listSources(File sourceDir, Digester digester) throws Exception {
		for (File file : sourceDir.listFiles()) {
			if (file.isDirectory()) {
				listSources(file, digester);
			} else if (file.getName().indexOf(".xml") > 0) {
				digest(file, digester);
			} else {
				LOG.info("Skipping non xml file " + file.getAbsolutePath() + " in DD load");
			}
		}
	}

	protected void setupDigester(Digester digester) {
		setCurrentDigester(digester);
		digester.push(dataDictionary);

	}

	protected void digest(InputStream inputStream, String fileName, Digester digester) throws Exception {
		setupDigester(digester);
		setCurrentFilename(fileName);
		digester.setErrorHandler(new XmlErrorHandler(fileName));
		digester.parse(inputStream);
	}

	protected void digest(File file, Digester digester) throws Exception {
		setupDigester(digester);
		digester.setErrorHandler(new XmlErrorHandler(file.getName()));
		setCurrentFilename(file.getName());
		digester.parse(file);
	}

	/**
	 * @return Rules loaded from the appropriate XML file
	 */
	protected Rules loadRules() {
		// locate Digester rules
		URL rulesUrl = DataDictionaryBuilder.class.getResource(PACKAGE_PREFIX + "digesterRules.xml");
		if (rulesUrl == null) {
			throw new InitException("unable to locate digester rules file");
		}

		// create and init digester
		Digester digester = DigesterLoader.createDigester(rulesUrl);

		return digester.getRules();
	}

	/**
	 * @return fully-initialized Digester used to process entry XML files
	 */
	protected Digester buildDigester(Rules rules) {
		Digester digester = new Digester();
		digester.setNamespaceAware(false);
		digester.setValidating(true);

		// register DTD(s)
		for (int i = 0; i < DTD_REGISTRATION_INFO.length; ++i) {
			String dtdPublic = DTD_REGISTRATION_INFO[i][0];
			String dtdPath = DTD_REGISTRATION_INFO[i][1];

			URL dtdUrl = DataDictionaryBuilder.class.getResource(dtdPath);
			if (dtdUrl == null) {
				throw new InitException("unable to locate DTD at \"" + dtdPath + "\"");
			}
			digester.register(dtdPublic, dtdUrl.toString());
		}

		digester.setRules(rules);

		return digester;
	}

	/**
	 * This is a rather ugly hack which expose the Digester being used to parse
	 * a given XML file so that error messages generated during parsing can
	 * contain file and line number info.
	 * <p>
	 * If we weren't using an XML file to configure Digester, I'd do this by
	 * rewriting all of the rules so that they accepted the Digester instance as
	 * a param, which would be considerably less ugly.
	 */

	/**
	 * @return name of the XML file currently being parsed
	 * @throws IllegalStateException
	 *             if parsing is not in progress
	 */
	public static String getCurrentFileName() {
		// try to prevent invalid access to nonexistent filename
		if (currentFilename == null) {
			throw new IllegalStateException("current filename is null");
		}

		return currentFilename;
	}

	/**
	 * @return line number in the XML file currently being parsed
	 * @throws IllegalStateException
	 *             if parsing is not in progress
	 */
	public static int getCurrentLineNumber() {
		Locator locator = getCurrentDigester().getDocumentLocator();
		int lineNumber = locator.getLineNumber();

		return lineNumber;
	}

	private static Digester currentDigester;

	protected static void setCurrentDigester(Digester newDigester) {
		currentDigester = newDigester;
	}

	protected static void clearCurrentDigester() {
		currentDigester = null;
	}

	protected static Digester getCurrentDigester() {
		// try to prevent invalid access to nonexistent digester instance
		if (currentDigester == null) {
			throw new IllegalStateException("current digester is null");
		}

		return currentDigester;
	}

	private static String currentFilename;

	protected void setCurrentFilename(String newFilename) {
		currentFilename = newFilename;
	}

	protected void clearCurrentFilename() {
		currentFilename = null;
	}

	protected boolean getExceptionsOccurred() {
		return exceptionsOccurred;
	}

	protected void setExceptionsOccurred(boolean exceptionsOccured) {
		this.exceptionsOccurred = exceptionsOccured;
	}

	public void setKualiConfigurationService(KualiConfigurationService kualiConfigurationService) {
		this.kualiConfigurationService = kualiConfigurationService;
	}

	public KualiConfigurationService getKualiConfigurationService() {
		return kualiConfigurationService;
	}

	public void setKualiGroupService(KualiGroupService kualiGroupService) {
		this.kualiGroupService = kualiGroupService;
	}

	public KualiGroupService getKualiGroupService() {
		return kualiGroupService;
	}
}
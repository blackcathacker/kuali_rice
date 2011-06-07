package org.kuali.rice.ksb.api.registry;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.util.jaxb.QNameAsStringAdapter;
import org.kuali.rice.ksb.api.KsbApiConstants;

/**
 * Defines the interface for a remotely accessible service registry.  Applications
 * can query for information about available services through the apis provided
 * as well as publishing their own services.
 * 
 * <p>The {{ServiceRegistry}} deals primarily with the concept of a
 * {@link ServiceEndpoint} which holds a {@link ServiceInfo}
 * and a {@link ServiceDescriptor}.  These pieces include information about the
 * service and it's configuration which might be needed by applications wishing to
 * invoke those services.
 * 
 * <p>Many of the operations on the {{ServiceRegistry}} only return the 
 * {{ServiceInfo}}.  This is because retrieving the full {{ServiceDescriptor}}
 * is a more expensive operation (since it consists of a serialized XML
 * representation of the service's configuration which needs to be unmarshaled
 * and processed) and typically the descriptor is only needed when the client
 * application actually wants to connect to the service.
 * 
 * <p>The {@link ServiceInfo} provides two important pieces of information which
 *  help the registry (and the applications which interact with it) understand
 *  who the owner of a service is.  The first of these is the "application id"
 *  which identifies the application which owns the service.  In terms of
 *  Kuali Rice, an "application" is an abstract concept and consist of multiple
 *  instances of an application which are essentially mirrors of each and
 *  publish the same set of services.  Each of these individuals instances of
 *  an application is identified by the "instance id" which is also available
 *  from the {{ServiceInfo}}.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@WebService(name = "serviceRegistrySoap", targetNamespace = KsbApiConstants.Namespaces.KSB_NAMESPACE_2_0)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ServiceRegistry {
	
	/**
	 * Returns an unmodifiable list of {@link ServiceInfo} for all services that have a status
	 * of {@link ServiceEndpointStatus#ONLINE} with the given name.  If there
	 * are no services with the given name, this method should return an empty
	 * list.
	 * 
	 * <p>It is typical in clustered environments and other situations that
	 * more than one online service might be available for a given service name.
	 * It is intended that a client of the registry will use an available endpoint
	 * of their choosing to connect to and invoke the service.
	 * 
	 * @param serviceName the name of the service to locate
	 * @return an unmodifiable list of {{ServiceInfo}} for online services with the given name.
	 * If no services were found, an empty list will be returned, but this method should never
	 * return null.
	 * 
	 * @throws RiceIllegalArgumentException if serviceName is null
	 */
	@WebMethod(operationName = "getOnlineServiceByName")
	@WebResult(name = "serviceInfos")
	@XmlElementWrapper(name = "serviceInfos", required = true)
	@XmlElement(name = "serviceInfo", required = false)
	List<ServiceInfo> getOnlineServicesByName(
			@XmlJavaTypeAdapter(QNameAsStringAdapter.class)
			@WebParam(name = "serviceName")
			QName serviceName) throws RiceIllegalArgumentException;
	
	/**
	 * Returns an unmodifiable list of {@link ServiceInfo} for all services in
	 * the registry that have a status of {@link ServiceEndpointStatus#ONLINE}.
	 * If there are no online services in the registry, this method will return
	 * an empty list.
	 * 
	 * @return an unmodifiable list of {{ServiceInfo}} for all online services
	 * in the registry. If no services were found, an empty list will be
	 * returned, but this method should never return null.
	 */
	@WebMethod(operationName = "getAllOnlineServices")
	@WebResult(name = "serviceInfo")
	@XmlElementWrapper(name = "serviceInfos", required = true)
	@XmlElement(name = "serviceInfo", required = false)
	List<ServiceInfo> getAllOnlineServices();
	
	/**
	 * Returns an unmodifiable list of {@link ServiceInfo} for all services in
	 * the registry.  If there are no services in the registry, this method will
	 * return an empty list.
	 * 
	 * @return an unmodifiable list of {{ServiceInfo}} for all services in the
	 * registry. If no services were found, an empty list will be returned, but
	 * this method should never return null.
	 */
	@WebMethod(operationName = "getAllServices")
	@WebResult(name = "serviceInfo")
	@XmlElementWrapper(name = "serviceInfos", required = true)
	@XmlElement(name = "serviceInfo", required = false)
	List<ServiceInfo> getAllServices();
	
	@WebMethod(operationName = "getAllServicesForInstance")
	@WebResult(name = "serviceInfos")
	@XmlElementWrapper(name = "serviceInfos", required = true)
	@XmlElement(name = "serviceInfo", required = false)
	List<ServiceInfo> getAllServicesForInstance(@WebParam(name = "instanceId") String instanceId) throws RiceIllegalArgumentException;
	
	@WebMethod(operationName = "getServiceDescriptor")
	@WebResult(name = "serviceDescriptor")
	@XmlElement(name = "serviceDescriptor", required = false)
	ServiceDescriptor getServiceDescriptor(@WebParam(name = "serviceDescriptorId") String serviceDescriptorId) throws RiceIllegalArgumentException;
	
	@WebMethod(operationName = "getServiceDescriptors")
	@WebResult(name = "serviceDescriptors")
	@XmlElementWrapper(name = "serviceDescriptors", required = true)
	@XmlElement(name = "serviceDescriptor", required = false)
	List<ServiceDescriptor> getServiceDescriptors(@WebParam(name = "serviceDescriptorId") List<String> serviceDescriptorIds) throws RiceIllegalArgumentException;
	
	@WebMethod(operationName = "publishService")
	@WebResult(name = "serviceEndpoint")
	@XmlElement(name = "serviceEndpoint", required = true)
	ServiceEndpoint publishService(@WebParam(name = "serviceEndpoint") ServiceEndpoint serviceEndpoint) throws RiceIllegalArgumentException;

	@WebMethod(operationName = "publishServices")
	@WebResult(name = "serviceEndpoints")
	@XmlElementWrapper(name = "serviceEndpoints", required = true)
	@XmlElement(name = "serviceEndpoint", required = false)
	List<ServiceEndpoint> publishServices(@WebParam(name = "serviceEndpoint") List<ServiceEndpoint> serviceEndpoints) throws RiceIllegalArgumentException;
		
	@WebMethod(operationName = "removeServiceEndpoint")
	@WebResult(name = "serviceEndpoint")
	@XmlElement(name = "serviceEndpoint", required = false)
	ServiceEndpoint removeServiceEndpoint(@WebParam(name = "serviceId") String serviceId) throws RiceIllegalArgumentException;
	
	@WebMethod(operationName = "removeServiceEndpoints")
	@WebResult(name = "serviceEndpoints")
	@XmlElementWrapper(name = "serviceEndpoints", required = true)
	@XmlElement(name = "serviceEndpoint", required = false)
	List<ServiceEndpoint> removeServiceEndpoints(@WebParam(name = "serviceId") List<String> serviceIds) throws RiceIllegalArgumentException;
	
	@WebMethod(operationName = "removeAndPublish")
	@WebResult(name = "removeAndPublishResult")
	@XmlElement(name = "removeAndPublishResult", required = true)
	RemoveAndPublishResult removeAndPublish(@WebParam(name = "removeServiceId") List<String> removeServiceIds,
			@WebParam(name = "publishServiceEndpoint") List<ServiceEndpoint> publishServiceEndpoints);
	
	@WebMethod(operationName = "updateStatus")
	void updateStatus(@WebParam(name = "serviceId") String serviceId, @WebParam(name = "status") String status) throws RiceIllegalArgumentException;
	
	@WebMethod(operationName = "updateStatuses")
	void updateStatuses(@WebParam(name = "serviceId") List<String> serviceIds, @WebParam(name = "status") String status) throws RiceIllegalArgumentException;
	
	@WebMethod(operationName = "takeInstanceOffline")
	void takeInstanceOffline(@WebParam(name = "instanceId") String instanceId) throws RiceIllegalArgumentException;
	
}

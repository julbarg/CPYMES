/**
 * IvrcmdbLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package CMBD;

public class IvrcmdbLocator extends org.apache.axis.client.Service implements CMBD.Ivrcmdb {

    public IvrcmdbLocator() {
    }


    public IvrcmdbLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IvrcmdbLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IvrcmdbWsImplPort
    private java.lang.String IvrcmdbWsImplPort_address = "http://172.31.239.242:8080/IvrCMDB/services/Ivrcmdb";

    public java.lang.String getIvrcmdbWsImplPortAddress() {
        return IvrcmdbWsImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IvrcmdbWsImplPortWSDDServiceName = "IvrcmdbWsImplPort";

    public java.lang.String getIvrcmdbWsImplPortWSDDServiceName() {
        return IvrcmdbWsImplPortWSDDServiceName;
    }

    public void setIvrcmdbWsImplPortWSDDServiceName(java.lang.String name) {
        IvrcmdbWsImplPortWSDDServiceName = name;
    }

    public CMBD.ExtractServices getIvrcmdbWsImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IvrcmdbWsImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIvrcmdbWsImplPort(endpoint);
    }

    public CMBD.ExtractServices getIvrcmdbWsImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            CMBD.IvrcmdbSoapBindingStub _stub = new CMBD.IvrcmdbSoapBindingStub(portAddress, this);
            _stub.setPortName(getIvrcmdbWsImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIvrcmdbWsImplPortEndpointAddress(java.lang.String address) {
        IvrcmdbWsImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (CMBD.ExtractServices.class.isAssignableFrom(serviceEndpointInterface)) {
                CMBD.IvrcmdbSoapBindingStub _stub = new CMBD.IvrcmdbSoapBindingStub(new java.net.URL(IvrcmdbWsImplPort_address), this);
                _stub.setPortName(getIvrcmdbWsImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IvrcmdbWsImplPort".equals(inputPortName)) {
            return getIvrcmdbWsImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://co.com.claro", "Ivrcmdb");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://co.com.claro", "IvrcmdbWsImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IvrcmdbWsImplPort".equals(portName)) {
            setIvrcmdbWsImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

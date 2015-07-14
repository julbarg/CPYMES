/**
 * ExtractServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package claro.com.co;

public interface ExtractServices extends java.rmi.Remote {
    public claro.com.co.ServicesDevicesDTO[] extractServicesPort(java.lang.String ip, java.lang.String port, java.lang.String hostname) throws java.rmi.RemoteException;
    public claro.com.co.ServicesDevicesDTO[] extractServicesCod(java.lang.String codService) throws java.rmi.RemoteException;
    public claro.com.co.ServicesDevicesDTO[] extractServicesIp(java.lang.String ip) throws java.rmi.RemoteException;
}

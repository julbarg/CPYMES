/**
 * ExtractServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package CMBD;

public interface ExtractServices extends java.rmi.Remote {
    public CMBD.ServicesDevicesDTO[] extractServicesPort(java.lang.String ip, java.lang.String port, java.lang.String hostname) throws java.rmi.RemoteException;
    public CMBD.ServicesDevicesDTO[] extractServicesCod(java.lang.String codService) throws java.rmi.RemoteException;
    public CMBD.ServicesDevicesDTO[] extractServicesPortTrunk(java.lang.String ip, java.lang.String port, java.lang.String hostname) throws java.rmi.RemoteException;
    public CMBD.ServicesDevicesDTO[] extractServicesIp(java.lang.String ip) throws java.rmi.RemoteException;
}

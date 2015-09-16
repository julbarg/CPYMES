package CMBD;

public class ExtractServicesProxy implements CMBD.ExtractServices {
  private String _endpoint = null;
  private CMBD.ExtractServices extractServices = null;
  
  public ExtractServicesProxy() {
    _initExtractServicesProxy();
  }
  
  public ExtractServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initExtractServicesProxy();
  }
  
  private void _initExtractServicesProxy() {
    try {
      extractServices = (new CMBD.IvrcmdbLocator()).getIvrcmdbWsImplPort();
      if (extractServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)extractServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)extractServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (extractServices != null)
      ((javax.xml.rpc.Stub)extractServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CMBD.ExtractServices getExtractServices() {
    if (extractServices == null)
      _initExtractServicesProxy();
    return extractServices;
  }
  
  public CMBD.ServicesDevicesDTO[] extractServicesPort(java.lang.String ip, java.lang.String port, java.lang.String hostname) throws java.rmi.RemoteException{
    if (extractServices == null)
      _initExtractServicesProxy();
    return extractServices.extractServicesPort(ip, port, hostname);
  }
  
  public CMBD.ServicesDevicesDTO[] extractServicesCod(java.lang.String codService) throws java.rmi.RemoteException{
    if (extractServices == null)
      _initExtractServicesProxy();
    return extractServices.extractServicesCod(codService);
  }
  
  public CMBD.ServicesDevicesDTO[] extractServicesPortTrunk(java.lang.String ip, java.lang.String port, java.lang.String hostname) throws java.rmi.RemoteException{
    if (extractServices == null)
      _initExtractServicesProxy();
    return extractServices.extractServicesPortTrunk(ip, port, hostname);
  }
  
  public CMBD.ServicesDevicesDTO[] extractServicesIp(java.lang.String ip) throws java.rmi.RemoteException{
    if (extractServices == null)
      _initExtractServicesProxy();
    return extractServices.extractServicesIp(ip);
  }
  
  
}
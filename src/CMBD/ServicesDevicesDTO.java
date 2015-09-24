/** ServicesDevicesDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter. */

package CMBD;

public class ServicesDevicesDTO implements java.io.Serializable {
   /**
   * 
   */
   private static final long serialVersionUID = -5186169310519494503L;

   private java.lang.String city;

   private java.lang.String description;

   private java.lang.String device;

   private java.lang.String deviceType;

   private java.lang.String divisional;

   private java.lang.String ip;

   private java.lang.String port;

   private java.lang.String sds;

   private java.lang.String service;

   private java.lang.String[] serviceList;

   public ServicesDevicesDTO() {
   }

   public ServicesDevicesDTO(java.lang.String city, java.lang.String description, java.lang.String device,
      java.lang.String deviceType, java.lang.String divisional, java.lang.String ip, java.lang.String port,
      java.lang.String sds, java.lang.String service, java.lang.String[] serviceList) {
      this.city = city;
      this.description = description;
      this.device = device;
      this.deviceType = deviceType;
      this.divisional = divisional;
      this.ip = ip;
      this.port = port;
      this.sds = sds;
      this.service = service;
      this.serviceList = serviceList;
   }

   /**
    * Gets the city value for this ServicesDevicesDTO.
    * 
    * @return city
    */
   public java.lang.String getCity() {
      return city;
   }

   /**
    * Sets the city value for this ServicesDevicesDTO.
    * 
    * @param city
    */
   public void setCity(java.lang.String city) {
      this.city = city;
   }

   /**
    * Gets the description value for this ServicesDevicesDTO.
    * 
    * @return description
    */
   public java.lang.String getDescription() {
      return description;
   }

   /**
    * Sets the description value for this ServicesDevicesDTO.
    * 
    * @param description
    */
   public void setDescription(java.lang.String description) {
      this.description = description;
   }

   /**
    * Gets the device value for this ServicesDevicesDTO.
    * 
    * @return device
    */
   public java.lang.String getDevice() {
      return device;
   }

   /**
    * Sets the device value for this ServicesDevicesDTO.
    * 
    * @param device
    */
   public void setDevice(java.lang.String device) {
      this.device = device;
   }

   /**
    * Gets the deviceType value for this ServicesDevicesDTO.
    * 
    * @return deviceType
    */
   public java.lang.String getDeviceType() {
      return deviceType;
   }

   /**
    * Sets the deviceType value for this ServicesDevicesDTO.
    * 
    * @param deviceType
    */
   public void setDeviceType(java.lang.String deviceType) {
      this.deviceType = deviceType;
   }

   /**
    * Gets the divisional value for this ServicesDevicesDTO.
    * 
    * @return divisional
    */
   public java.lang.String getDivisional() {
      return divisional;
   }

   /**
    * Sets the divisional value for this ServicesDevicesDTO.
    * 
    * @param divisional
    */
   public void setDivisional(java.lang.String divisional) {
      this.divisional = divisional;
   }

   /**
    * Gets the ip value for this ServicesDevicesDTO.
    * 
    * @return ip
    */
   public java.lang.String getIp() {
      return ip;
   }

   /**
    * Sets the ip value for this ServicesDevicesDTO.
    * 
    * @param ip
    */
   public void setIp(java.lang.String ip) {
      this.ip = ip;
   }

   /**
    * Gets the port value for this ServicesDevicesDTO.
    * 
    * @return port
    */
   public java.lang.String getPort() {
      return port;
   }

   /**
    * Sets the port value for this ServicesDevicesDTO.
    * 
    * @param port
    */
   public void setPort(java.lang.String port) {
      this.port = port;
   }

   /**
    * Gets the sds value for this ServicesDevicesDTO.
    * 
    * @return sds
    */
   public java.lang.String getSds() {
      return sds;
   }

   /**
    * Sets the sds value for this ServicesDevicesDTO.
    * 
    * @param sds
    */
   public void setSds(java.lang.String sds) {
      this.sds = sds;
   }

   /**
    * Gets the service value for this ServicesDevicesDTO.
    * 
    * @return service
    */
   public java.lang.String getService() {
      return service;
   }

   /**
    * Sets the service value for this ServicesDevicesDTO.
    * 
    * @param service
    */
   public void setService(java.lang.String service) {
      this.service = service;
   }

   /**
    * Gets the serviceList value for this ServicesDevicesDTO.
    * 
    * @return serviceList
    */
   public java.lang.String[] getServiceList() {
      return serviceList;
   }

   /**
    * Sets the serviceList value for this ServicesDevicesDTO.
    * 
    * @param serviceList
    */
   public void setServiceList(java.lang.String[] serviceList) {
      this.serviceList = serviceList;
   }

   public java.lang.String getServiceList(int i) {
      return this.serviceList[i];
   }

   public void setServiceList(int i, java.lang.String _value) {
      this.serviceList[i] = _value;
   }

   private java.lang.Object __equalsCalc = null;

   public synchronized boolean equals(java.lang.Object obj) {
      if (!(obj instanceof ServicesDevicesDTO))
         return false;
      ServicesDevicesDTO other = (ServicesDevicesDTO) obj;
      if (obj == null)
         return false;
      if (this == obj)
         return true;
      if (__equalsCalc != null) {
         return (__equalsCalc == obj);
      }
      __equalsCalc = obj;
      boolean _equals;
      _equals = true
         && ((this.city == null && other.getCity() == null) || (this.city != null && this.city.equals(other.getCity())))
         && ((this.description == null && other.getDescription() == null) || (this.description != null && this.description
            .equals(other.getDescription())))
         && ((this.device == null && other.getDevice() == null) || (this.device != null && this.device.equals(other
            .getDevice())))
         && ((this.deviceType == null && other.getDeviceType() == null) || (this.deviceType != null && this.deviceType
            .equals(other.getDeviceType())))
         && ((this.divisional == null && other.getDivisional() == null) || (this.divisional != null && this.divisional
            .equals(other.getDivisional())))
         && ((this.ip == null && other.getIp() == null) || (this.ip != null && this.ip.equals(other.getIp())))
         && ((this.port == null && other.getPort() == null) || (this.port != null && this.port.equals(other.getPort())))
         && ((this.sds == null && other.getSds() == null) || (this.sds != null && this.sds.equals(other.getSds())))
         && ((this.service == null && other.getService() == null) || (this.service != null && this.service.equals(other
            .getService())))
         && ((this.serviceList == null && other.getServiceList() == null) || (this.serviceList != null && java.util.Arrays
            .equals(this.serviceList, other.getServiceList())));
      __equalsCalc = null;
      return _equals;
   }

   private boolean __hashCodeCalc = false;

   public synchronized int hashCode() {
      if (__hashCodeCalc) {
         return 0;
      }
      __hashCodeCalc = true;
      int _hashCode = 1;
      if (getCity() != null) {
         _hashCode += getCity().hashCode();
      }
      if (getDescription() != null) {
         _hashCode += getDescription().hashCode();
      }
      if (getDevice() != null) {
         _hashCode += getDevice().hashCode();
      }
      if (getDeviceType() != null) {
         _hashCode += getDeviceType().hashCode();
      }
      if (getDivisional() != null) {
         _hashCode += getDivisional().hashCode();
      }
      if (getIp() != null) {
         _hashCode += getIp().hashCode();
      }
      if (getPort() != null) {
         _hashCode += getPort().hashCode();
      }
      if (getSds() != null) {
         _hashCode += getSds().hashCode();
      }
      if (getService() != null) {
         _hashCode += getService().hashCode();
      }
      if (getServiceList() != null) {
         for (int i = 0; i < java.lang.reflect.Array.getLength(getServiceList()); i++) {
            java.lang.Object obj = java.lang.reflect.Array.get(getServiceList(), i);
            if (obj != null && !obj.getClass().isArray()) {
               _hashCode += obj.hashCode();
            }
         }
      }
      __hashCodeCalc = false;
      return _hashCode;
   }

   // Type metadata
   private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ServicesDevicesDTO.class, true);

   static {
      typeDesc.setXmlType(new javax.xml.namespace.QName("http://co.com.claro", "servicesDevicesDTO"));
      org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("city");
      elemField.setXmlName(new javax.xml.namespace.QName("", "city"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("description");
      elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("device");
      elemField.setXmlName(new javax.xml.namespace.QName("", "device"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("deviceType");
      elemField.setXmlName(new javax.xml.namespace.QName("", "deviceType"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("divisional");
      elemField.setXmlName(new javax.xml.namespace.QName("", "divisional"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("ip");
      elemField.setXmlName(new javax.xml.namespace.QName("", "ip"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("port");
      elemField.setXmlName(new javax.xml.namespace.QName("", "port"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("sds");
      elemField.setXmlName(new javax.xml.namespace.QName("", "sds"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("service");
      elemField.setXmlName(new javax.xml.namespace.QName("", "service"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new org.apache.axis.description.ElementDesc();
      elemField.setFieldName("serviceList");
      elemField.setXmlName(new javax.xml.namespace.QName("", "serviceList"));
      elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      elemField.setMaxOccursUnbounded(true);
      typeDesc.addFieldDesc(elemField);
   }

   /**
    * Return type metadata object
    */
   public static org.apache.axis.description.TypeDesc getTypeDesc() {
      return typeDesc;
   }

   /**
    * Get Custom Serializer
    */
   public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
      java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
      return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
   }

   /**
    * Get Custom Deserializer
    */
   public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
      java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
      return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
   }

}

package com.claro.cpymes.util;

import java.math.BigDecimal;

/**
 * Define las contantes de la aplicacion
 * @author jbarragan
 *
 */
public class Constant {

   public static final String PATH_CONFIG_PROPERTIES = "/usr/share/tomee/conf/CPyMES.properties";

   public static final String ESTADO = "estado";

   public static final String METHOD = "method";

   public static final String METHOD_DAEMON = "daemon";

   public static final String METHOD_LISTENER = "listener";

   public static final String PATH_CONNECTION_LISTENER = Util.getProperties("path_snmptt");

   public static final String PATH_DRL_FILE = Util.getProperties("path_drl_file");

   public static final String PATH_DRL_FILE_CEP = Util.getProperties("path_drl_file_cep");

   public static final String DELIMETER_SNMPTT = Util.getProperties("delimeter_snmptt");

   public static final String DELIMETER_IP = Util.getProperties("delimeter_ip");

   public static final String TIMER_SIMILAR_REGISTERS = Util.getProperties("timer_similar_register");

   public static final String REGEX_INTERFACE = Util.getProperties("regex_interface");

   public static final String REGEX_LOWECASE = "[a-z]";

   public static final String REGEX_UPPERCASECASE_ALONE = "[\\s][A-Z]{1,2}[\\s]";

   public static final String REGEX_UPPERCASECASE_ALONE_2 = "^([A-Z]{1}[\\s])";

   public static final String REGEX_GUION = "-";

   public static final String REGEX_GUION_NUMBER = "([-]{1}[0-9]{1})";

   public static final String REGEX_POINT_NUMBER = "([.]{1}[0-9]{1})";

   public static final String REGEX_WHITESPACE_INIT = "^([\\s]+)";

   public static final String REGEX_WHITESPACE = "[\\s]+";

   public static final int TIMER_DAEMON = Util.getPropertiesInt("timer_daeomon") != 0 ? Util
      .getPropertiesInt("timer_daeomon") : 30000;

   public static final int NUMBER_ALARMS_CORRELATE = Util.getPropertiesInt("number_alarms_correlate");

   public static final int NUMBER_ALARMS_CORRELATE_MAX = Util.getPropertiesInt("number_alarms_correlate_max");

   public static final int TIME_RECOGNIZE_CORRELATION = Util.getPropertiesInt("time_recognize_correlation");

   public static final int MAXIME_RESULT_LOGS = Util.getPropertiesInt("maxime_result_logs");

   public static final int MAXIME_RESULT_ALARM = Util.getPropertiesInt("maxime_result_alarms");

   public static final String BOLD_INIT = "<b>";

   public static final String BOLD_END = "</b>";

   public static final String INTERFACE = " interface ";

   public static final String DESCRIPCION = " descripción ";

   public static final int MAX_LENGHT_TEXT = 50000;

   public static final String TRUNK = "TRUNK";

   public static final String USER_NAME = "userName";

   public static final String CHARACTER_ESPECIAL = "Ã±";

   public static final String SERVER_FTP = Util.getProperties("server_ftp");

   public static final int PORT_FTP = Util.getPropertiesInt("port_ftp");

   public static final String USER_NAME_FTP = Util.getProperties("user_name_ftp");

   public static final String PASSWORD_FTP = Util.getProperties("password_ftp");

   public static final String FTP_WORKING_DIR = Util.getProperties("ftp_working_dir");

   public static final String SERVER_WORKING_DIR = Util.getProperties("server_working_dir");

   public static final String NAME_FILE_NITS = Util.getProperties("name_file_nits");

   public static final String PROTOCOL_FTP = Util.getProperties("protocol_ftp");

   public static final String ACTIVADO = "Activado";

   public static final int TIMER_LOAD_NITS = Util.getPropertiesInt("timer_load_nits");

   public static final int PERCENTAGE_DIFFERENCE_NITS = 1;

   public static final String FECHA_ULTIMO_CARGUE_NITS = "fechaUltimoCargueNits";

   public static final BigDecimal CODIGO_AUDIO_IVR = new BigDecimal(2354);

}

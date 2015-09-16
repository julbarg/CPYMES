package com.claro.cpymes.ejb.remote;

import javax.ejb.Remote;

import com.claro.cpymes.dto.ControlReportDTO;


@Remote
public interface ControlEJBRemote {

   ControlReportDTO loadControl() throws Exception;

}

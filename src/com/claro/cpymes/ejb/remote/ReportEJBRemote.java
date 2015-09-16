package com.claro.cpymes.ejb.remote;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.claro.cpymes.dto.DataDTO;
import com.claro.cpymes.dto.ReportDTO;


@Remote
public interface ReportEJBRemote {

   ArrayList<DataDTO> findDataByFilter(ReportDTO reportDTO) throws Exception;

   void generateReport(ArrayList<DataDTO> listData) throws Exception;

}

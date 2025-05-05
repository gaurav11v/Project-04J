
package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SupplierBean;
import com.rays.pro4.Bean.SupplierBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.CollegeModel;
import com.rays.pro4.Model.SupplierModel;
import com.rays.pro4.Model.SupplierModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SupplierListCtl" , urlPatterns = {"/ctl/SupplierListCtl"})
public class SupplierListCtl extends BaseCtl {
	/** The log. */
    private static Logger log = Logger.getLogger(SupplierListCtl.class);
    
    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
  
    
    
    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        SupplierBean bean = new SupplierBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setCategory(DataUtility.getString(request.getParameter("category")));
        bean.setSupplierDate(DataUtility.getDate(request.getParameter("dob")));
        bean.setPayment(DataUtility.getString(request.getParameter("payment")));
      
         
        return bean;
    }

    /**
     * Contains Display logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("SupplierListCtl doGet Start");
        List list;

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        SupplierBean bean = (SupplierBean) populateBean(request);
        SupplierModel model = new SupplierModel();
        
        String op = DataUtility.getString(request.getParameter("operation"));
     //   String[] ids = request.getParameterValues("ids");

        try {
        	
            list = model.search(bean, pageNo, pageSize);
 //            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("SupplierListCtl doGet End");
    }

    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("SupplierListCtl doPost Start");
        List list = null;
        String op = DataUtility.getString(request.getParameter("operation"));
        
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        SupplierBean bean = (SupplierBean) populateBean(request);
        
        String[] ids = request.getParameterValues("ids");
        SupplierModel model = new SupplierModel();


                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
                    pageNo--;
                }
                else if (OP_NEW.equalsIgnoreCase(op)) {
        			ServletUtility.redirect(ORSView.SUPPLIER_CTL, request, response);
        			return;
        		}else if (OP_RESET.equalsIgnoreCase(op)) {
        			ServletUtility.redirect(ORSView.SUPPLIER_LIST_CTL, request, response);
        			return;
        		}  
                else if (OP_DELETE.equalsIgnoreCase(op)) {
                    pageNo = 1;
                    if (ids != null && ids.length > 0) {
                    	SupplierBean deletebean = new SupplierBean();
                   
                        for (String id : ids) {
                            deletebean.setId(DataUtility.getInt(id));
                            try {
                				model.delete(deletebean);
    							
    						} catch (ApplicationException e) {
    							e.printStackTrace();
    							ServletUtility.handleException(e, request, response);
    							return;
    						}System.out.println("20");
                            ServletUtility.setSuccessMessage(" Supplier Data Successfully Deleted", request);
                        }
                    } 
                    else {
                        ServletUtility.setErrorMessage(
                                "Select at least one record", request);
                    }
                }

           
            try {
				list = model.search(bean, pageNo, pageSize);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setBean(bean, request);
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        log.debug("SupplierListCtl doGet End");
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.SUPPLIER_LIST_VIEW;
    }
}
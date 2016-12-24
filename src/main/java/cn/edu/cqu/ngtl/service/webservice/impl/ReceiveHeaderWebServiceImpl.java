package cn.edu.cqu.ngtl.service.webservice.impl;

import cn.edu.cqu.ngtl.service.webservice.ReceiveHeaderWebService;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * Created by awake on 2016/11/24.
 */
public class ReceiveHeaderWebServiceImpl implements ReceiveHeaderWebService {

    @Override
    public boolean getResultFromHeaderWebService(String principalId, String permName) throws Exception {
        Boolean bool = false;
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            org.apache.cxf.endpoint.Client client = dcf.createClient("http://isse.cqu.edu.cn:80/exam/remoting/HeaderWebService?wsdl");
            client.getInInterceptors().add(new LoggingInInterceptor());
            client.getOutInterceptors().add(new LoggingOutInterceptor());
            Object[] objects = client.invoke("hasPermission", principalId, permName);
            bool = Boolean.parseBoolean(objects[0].toString());
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
        return bool;
    }


}

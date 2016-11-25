package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.service.common.HeaderService;
import cn.edu.cqu.ngtl.service.webservice.ReceiveHeaderWebService;
import cn.edu.cqu.ngtl.service.webservice.impl.ReceiveHeaderWebServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/11/24.
 */
public class HeaderServiceImpl implements HeaderService {



    /**
     * 按顺序存储菜单中四六级的链接出现与否
     */
    private static List<Boolean> examHeader;


    @Override
    public void getExamHeaderPermission(String principleId){
        if(examHeader==null){
            examHeader = new ArrayList<>();
        }
        String[] permissionName = new String[]{
                "ViewSetTimeInfoPagePermission",
                "ViewRoleManagerPagePermission",
                "ViewUserRoleManagerPagePermission",
                "ViewSystemParameterPermission",
                "ViewExmLogPagePermission",
                "ViewBaseInfoPagePermission",
                "ViewSyncUserInfoPagePermission",
                "ViewCetApplyPagePermission",
                "ViewInvigilatorApplicantPagePermission",
                "ViewTimetablePagePermission",
                "ViewInvigilatorPresetPagePermission",
                "ViewInvigilatorManagementPagePermission",
                "ViewSignCheckPagePermission",
                "ViewCollegeInvigilatorPagePermission",
                "ViewExmStudentPagePermission",
                "ViewApplicantInfoPagePermission",
                "ViewExamRoomPrintPagePermission",
                "ViewMessagePagePermission"
                };

        ReceiveHeaderWebService examWebService  =  new ReceiveHeaderWebServiceImpl();
        for(int i = 0;i < permissionName.length;i++){
            try {
                this.examHeader.add(examWebService.getResultFromHeaderWebService(principleId,permissionName[i]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Boolean> getExamHeader() {
        return examHeader;
    }

    public static void setExamHeader(List<Boolean> examHeader) {
        HeaderServiceImpl.examHeader = examHeader;
    }


}

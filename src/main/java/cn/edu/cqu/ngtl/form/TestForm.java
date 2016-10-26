package cn.edu.cqu.ngtl.form;

import cn.edu.cqu.ngtl.dataobject.TestObject;
import org.aspectj.weaver.ast.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 2016/10/6.
 */
public class TestForm extends BaseForm {
    private String inputField1;
    private String inputField2;
    private String inputField3;
    private String inputField4;
    private String inputField5;
    private String inputField6;
    private String inputField7;
    private String inputField8;
    private String inputField9;
    private String inputField10;
    private String inputField11;
    private String inputField12;
    private String inputField13;
    private String inputField14;
    private String inputField15;
    private String inputField16;
    private String inputField17;
    private String inputField18;
    private String inputField19;
    private String inputField20;
    private String inputField21;
    private String inputField22;
    private String inputField23;
    private String inputField24;
    private String inputField25;
    private String inputField26;
    private String inputField27;
    private String inputField28;
    private String inputField29;
    private String inputField30;
    private String inputField31;
    private String inputField32;
    private String inputField33;
    private String inputField34;
    private String inputField35;
    private String inputField36;
    private String inputField37;
    private String inputField38;
    private String inputField39;
    private String inputField40;
    private String inputField41;
    private String inputField42;
    private String inputField43;
    private String inputField44;
    private String inputField45;
    private String inputField46;
    private String inputField47;
    private String inputField48;
    private String inputField49;
    private String inputField50;
    private String inputField51;
    private String inputField52;
    private String inputField53;
    private String inputField54;
    private String inputField55;
    private String inputField56;
    private String inputField57;
    private String inputField58;
    private String inputField59;
    private String inputField60;
    private String inputField61;
    private String inputField62;
    private String inputField63;
    private String inputField64;
    private String inputField65;
    private String inputField66;
    private String inputField67;
    private String inputField68;
    private String inputField69;
    private String inputField70;
    private String inputField71;
    private String inputField72;
    private String inputField73;
    private String inputField74;
    private String inputField75;
    private String inputField76;
    private String inputField77;
    private String inputField78;
    private String inputField79;
    private String inputField80;
    private String inputField81;
    private String inputField82;
    private String inputField83;
    private String inputField84;
    private String inputField85;
    private String inputField86;
    private String inputField87;
    private String inputField88;
    private String inputField89;
    private String inputField90;
    private String inputField91;
    private String inputField92;
    private String inputField93;
    private String inputField94;
    private String inputField95;
    private String inputField96;
    private String inputField97;
    private String inputField98;
    private String inputField99;

    private String dialogInput1;
    private String dialogInput2;
    private String dialogInput3;
    private String dialogInput4;
    private String dialogInput5;
    private String dialogInput6;
    private String dialogInput7;
    private String dialogInput8;
    private String dialogInput9;
    private String dialogInput10;


    private String editorContent;

    private boolean radio1;
    private boolean radio2;
    private boolean radio3;
    private boolean radio4;
    private boolean radio5;
    private boolean radio6;
    private boolean radio7;
    private boolean radio8;
    private boolean radio9;
    private boolean radio10;
    private boolean radio11;
    private boolean radio12;
    private boolean radio13;
    private boolean radio14;
    private boolean radio15;
    private boolean radio16;
    private boolean radio17;
    private boolean radio18;
    private boolean radio19;
    private boolean radio20;
    private boolean radio21;
    private boolean radio22;
    private boolean radio23;
    private boolean radio24;
    private boolean radio25;
    private boolean radio26;
    private boolean radio27;
    private boolean radio28;
    private boolean radio29;


    private TestObject curObject;
    private TestObject newObject;
    private List<TestObject> collection = new ArrayList<TestObject>();

    public TestForm() {
        // 如果要用object传参，必须保证obj不是空值
        newObject=new TestObject();

        getCollection().add(new TestObject(new Date(2016,10,15), "14", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15),"20", "21", "22"));
        getCollection().add(new TestObject(new Date(2016,10,15),"8", "9", "10", "11", "12"));
        getCollection().add(new TestObject(new Date(2016,10,15),"2", "3", "4", "5", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15), "14", "15", "16", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15), "20", "21",  "24"));
        getCollection().add(new TestObject(new Date(2016,10,15), "143", "151", "126", "137", "164"));
        getCollection().add(new TestObject(new Date(2016,10,15), "144", "155", "166", "177", "188"));
        getCollection().add(new TestObject(new Date(2016,10,15),"14", "17", "17"));
        getCollection().add(new TestObject(new Date(2016,10,15), "5", "5", "4", "6", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15), "5", "5", "5", "5", "5"));
        getCollection().add(new TestObject(new Date(2016,10,15), "7", "3", "1", "9", "11"));
        getCollection().add(new TestObject(new Date(2016,10,15), "8", "9", "10", "11", "12"));
        getCollection().add(new TestObject(new Date(2016,10,15), "2", "3", "4", "5", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15),"14", "15", "16", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15),"20", "21", "22", "23", "24"));
        getCollection().add(new TestObject(new Date(2016,10,15), "143", "151", "126", "137", "164"));
        getCollection().add(new TestObject(new Date(2016,10,15), "144", "155", "166", "177", "188"));
        getCollection().add(new TestObject(new Date(2016,10,15), "14", "15", "15", "17", "17"));
        getCollection().add(new TestObject(new Date(2016,10,15),"5", "5", "4", "6", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15),"5", "5", "5", "5", "5"));
        getCollection().add(new TestObject(new Date(2016,10,15),"7", "3", "1", "9", "11"));
    }

    public TestObject getNewObject() {
        return newObject;
    }

    public void setNewObject(TestObject newObject) {
        this.newObject = newObject;
    }

    public String getDialogInput1() {
        return dialogInput1;
    }

    public void setDialogInput1(String dialogInput1) {
        this.dialogInput1 = dialogInput1;
    }

    public String getDialogInput2() {
        return dialogInput2;
    }

    public void setDialogInput2(String dialogInput2) {
        this.dialogInput2 = dialogInput2;
    }

    public String getDialogInput3() {
        return dialogInput3;
    }

    public void setDialogInput3(String dialogInput3) {
        this.dialogInput3 = dialogInput3;
    }

    public String getDialogInput4() {
        return dialogInput4;
    }

    public void setDialogInput4(String dialogInput4) {
        this.dialogInput4 = dialogInput4;
    }

    public String getDialogInput5() {
        return dialogInput5;
    }

    public void setDialogInput5(String dialogInput5) {
        this.dialogInput5 = dialogInput5;
    }

    public String getDialogInput6() {
        return dialogInput6;
    }

    public void setDialogInput6(String dialogInput6) {
        this.dialogInput6 = dialogInput6;
    }

    public String getDialogInput7() {
        return dialogInput7;
    }

    public void setDialogInput7(String dialogInput7) {
        this.dialogInput7 = dialogInput7;
    }

    public String getDialogInput8() {
        return dialogInput8;
    }

    public void setDialogInput8(String dialogInput8) {
        this.dialogInput8 = dialogInput8;
    }

    public String getDialogInput9() {
        return dialogInput9;
    }

    public void setDialogInput9(String dialogInput9) {
        this.dialogInput9 = dialogInput9;
    }

    public String getDialogInput10() {
        return dialogInput10;
    }

    public void setDialogInput10(String dialogInput10) {
        this.dialogInput10 = dialogInput10;
    }

    public TestObject getCurObject() {
        return curObject;
    }

    public void setCurObject(TestObject curObject) {
        this.curObject = curObject;
    }

    public String getEditorContent() {
        return editorContent;
    }

    public void setEditorContent(String editorContent) {
        this.editorContent = editorContent;
    }

    public boolean isRadio1() {
        return radio1;
    }

    public void setRadio1(boolean radio1) {
        this.radio1 = radio1;
    }

    public boolean isRadio2() {
        return radio2;
    }

    public void setRadio2(boolean radio2) {
        this.radio2 = radio2;
    }

    public boolean isRadio3() {
        return radio3;
    }

    public void setRadio3(boolean radio3) {
        this.radio3 = radio3;
    }

    public boolean isRadio4() {
        return radio4;
    }

    public void setRadio4(boolean radio4) {
        this.radio4 = radio4;
    }

    public boolean isRadio5() {
        return radio5;
    }

    public void setRadio5(boolean radio5) {
        this.radio5 = radio5;
    }

    public boolean isRadio6() {
        return radio6;
    }

    public void setRadio6(boolean radio6) {
        this.radio6 = radio6;
    }

    public boolean isRadio7() {
        return radio7;
    }

    public void setRadio7(boolean radio7) {
        this.radio7 = radio7;
    }

    public boolean isRadio8() {
        return radio8;
    }

    public void setRadio8(boolean radio8) {
        this.radio8 = radio8;
    }

    public boolean isRadio9() {
        return radio9;
    }

    public void setRadio9(boolean radio9) {
        this.radio9 = radio9;
    }

    public boolean isRadio10() {
        return radio10;
    }

    public void setRadio10(boolean radio10) {
        this.radio10 = radio10;
    }

    public boolean isRadio11() {
        return radio11;
    }

    public void setRadio11(boolean radio11) {
        this.radio11 = radio11;
    }

    public boolean isRadio12() {
        return radio12;
    }

    public void setRadio12(boolean radio12) {
        this.radio12 = radio12;
    }

    public boolean isRadio13() {
        return radio13;
    }

    public void setRadio13(boolean radio13) {
        this.radio13 = radio13;
    }

    public boolean isRadio14() {
        return radio14;
    }

    public void setRadio14(boolean radio14) {
        this.radio14 = radio14;
    }

    public boolean isRadio15() {
        return radio15;
    }

    public void setRadio15(boolean radio15) {
        this.radio15 = radio15;
    }

    public boolean isRadio16() {
        return radio16;
    }

    public void setRadio16(boolean radio16) {
        this.radio16 = radio16;
    }

    public boolean isRadio17() {
        return radio17;
    }

    public void setRadio17(boolean radio17) {
        this.radio17 = radio17;
    }

    public boolean isRadio18() {
        return radio18;
    }

    public void setRadio18(boolean radio18) {
        this.radio18 = radio18;
    }

    public boolean isRadio19() {
        return radio19;
    }

    public void setRadio19(boolean radio19) {
        this.radio19 = radio19;
    }

    public boolean isRadio20() {
        return radio20;
    }

    public void setRadio20(boolean radio20) {
        this.radio20 = radio20;
    }

    public boolean isRadio21() {
        return radio21;
    }

    public void setRadio21(boolean radio21) {
        this.radio21 = radio21;
    }

    public boolean isRadio22() {
        return radio22;
    }

    public void setRadio22(boolean radio22) {
        this.radio22 = radio22;
    }

    public boolean isRadio23() {
        return radio23;
    }

    public void setRadio23(boolean radio23) {
        this.radio23 = radio23;
    }

    public boolean isRadio24() {
        return radio24;
    }

    public void setRadio24(boolean radio24) {
        this.radio24 = radio24;
    }

    public boolean isRadio25() {
        return radio25;
    }

    public void setRadio25(boolean radio25) {
        this.radio25 = radio25;
    }

    public boolean isRadio26() {
        return radio26;
    }

    public void setRadio26(boolean radio26) {
        this.radio26 = radio26;
    }

    public boolean isRadio27() {
        return radio27;
    }

    public void setRadio27(boolean radio27) {
        this.radio27 = radio27;
    }

    public boolean isRadio28() {
        return radio28;
    }

    public void setRadio28(boolean radio28) {
        this.radio28 = radio28;
    }

    public boolean isRadio29() {
        return radio29;
    }

    public void setRadio29(boolean radio29) {
        this.radio29 = radio29;
    }

    public List<TestObject> getCollection() {
        return collection;
    }

    public void setCollection(List<TestObject> collection) {
        this.collection = collection;
    }

    public String getInputField31() {
        return inputField31;
    }

    public void setInputField31(String inputField31) {
        this.inputField31 = inputField31;
    }

    public String getInputField32() {
        return inputField32;
    }

    public void setInputField32(String inputField32) {
        this.inputField32 = inputField32;
    }

    public String getInputField33() {
        return inputField33;
    }

    public void setInputField33(String inputField33) {
        this.inputField33 = inputField33;
    }

    public String getInputField34() {
        return inputField34;
    }

    public void setInputField34(String inputField34) {
        this.inputField34 = inputField34;
    }

    public String getInputField35() {
        return inputField35;
    }

    public void setInputField35(String inputField35) {
        this.inputField35 = inputField35;
    }

    public String getInputField36() {
        return inputField36;
    }

    public void setInputField36(String inputField36) {
        this.inputField36 = inputField36;
    }

    public String getInputField37() {
        return inputField37;
    }

    public void setInputField37(String inputField37) {
        this.inputField37 = inputField37;
    }

    public String getInputField38() {
        return inputField38;
    }

    public void setInputField38(String inputField38) {
        this.inputField38 = inputField38;
    }

    public String getInputField39() {
        return inputField39;
    }

    public void setInputField39(String inputField39) {
        this.inputField39 = inputField39;
    }

    public String getInputField40() {
        return inputField40;
    }

    public void setInputField40(String inputField40) {
        this.inputField40 = inputField40;
    }

    public String getInputField41() {
        return inputField41;
    }

    public void setInputField41(String inputField41) {
        this.inputField41 = inputField41;
    }

    public String getInputField42() {
        return inputField42;
    }

    public void setInputField42(String inputField42) {
        this.inputField42 = inputField42;
    }

    public String getInputField43() {
        return inputField43;
    }

    public void setInputField43(String inputField43) {
        this.inputField43 = inputField43;
    }

    public String getInputField44() {
        return inputField44;
    }

    public void setInputField44(String inputField44) {
        this.inputField44 = inputField44;
    }

    public String getInputField45() {
        return inputField45;
    }

    public void setInputField45(String inputField45) {
        this.inputField45 = inputField45;
    }

    public String getInputField46() {
        return inputField46;
    }

    public void setInputField46(String inputField46) {
        this.inputField46 = inputField46;
    }

    public String getInputField47() {
        return inputField47;
    }

    public void setInputField47(String inputField47) {
        this.inputField47 = inputField47;
    }

    public String getInputField48() {
        return inputField48;
    }

    public void setInputField48(String inputField48) {
        this.inputField48 = inputField48;
    }

    public String getInputField49() {
        return inputField49;
    }

    public void setInputField49(String inputField49) {
        this.inputField49 = inputField49;
    }

    public String getInputField50() {
        return inputField50;
    }

    public void setInputField50(String inputField50) {
        this.inputField50 = inputField50;
    }

    public String getInputField51() {
        return inputField51;
    }

    public void setInputField51(String inputField51) {
        this.inputField51 = inputField51;
    }

    public String getInputField52() {
        return inputField52;
    }

    public void setInputField52(String inputField52) {
        this.inputField52 = inputField52;
    }

    public String getInputField53() {
        return inputField53;
    }

    public void setInputField53(String inputField53) {
        this.inputField53 = inputField53;
    }

    public String getInputField54() {
        return inputField54;
    }

    public void setInputField54(String inputField54) {
        this.inputField54 = inputField54;
    }

    public String getInputField55() {
        return inputField55;
    }

    public void setInputField55(String inputField55) {
        this.inputField55 = inputField55;
    }

    public String getInputField56() {
        return inputField56;
    }

    public void setInputField56(String inputField56) {
        this.inputField56 = inputField56;
    }

    public String getInputField57() {
        return inputField57;
    }

    public void setInputField57(String inputField57) {
        this.inputField57 = inputField57;
    }

    public String getInputField58() {
        return inputField58;
    }

    public void setInputField58(String inputField58) {
        this.inputField58 = inputField58;
    }

    public String getInputField59() {
        return inputField59;
    }

    public void setInputField59(String inputField59) {
        this.inputField59 = inputField59;
    }

    public String getInputField60() {
        return inputField60;
    }

    public void setInputField60(String inputField60) {
        this.inputField60 = inputField60;
    }

    public String getInputField61() {
        return inputField61;
    }

    public void setInputField61(String inputField61) {
        this.inputField61 = inputField61;
    }

    public String getInputField62() {
        return inputField62;
    }

    public void setInputField62(String inputField62) {
        this.inputField62 = inputField62;
    }

    public String getInputField63() {
        return inputField63;
    }

    public void setInputField63(String inputField63) {
        this.inputField63 = inputField63;
    }

    public String getInputField64() {
        return inputField64;
    }

    public void setInputField64(String inputField64) {
        this.inputField64 = inputField64;
    }

    public String getInputField65() {
        return inputField65;
    }

    public void setInputField65(String inputField65) {
        this.inputField65 = inputField65;
    }

    public String getInputField66() {
        return inputField66;
    }

    public void setInputField66(String inputField66) {
        this.inputField66 = inputField66;
    }

    public String getInputField67() {
        return inputField67;
    }

    public void setInputField67(String inputField67) {
        this.inputField67 = inputField67;
    }

    public String getInputField68() {
        return inputField68;
    }

    public void setInputField68(String inputField68) {
        this.inputField68 = inputField68;
    }

    public String getInputField69() {
        return inputField69;
    }

    public void setInputField69(String inputField69) {
        this.inputField69 = inputField69;
    }

    public String getInputField70() {
        return inputField70;
    }

    public void setInputField70(String inputField70) {
        this.inputField70 = inputField70;
    }

    public String getInputField71() {
        return inputField71;
    }

    public void setInputField71(String inputField71) {
        this.inputField71 = inputField71;
    }

    public String getInputField72() {
        return inputField72;
    }

    public void setInputField72(String inputField72) {
        this.inputField72 = inputField72;
    }

    public String getInputField73() {
        return inputField73;
    }

    public void setInputField73(String inputField73) {
        this.inputField73 = inputField73;
    }

    public String getInputField74() {
        return inputField74;
    }

    public void setInputField74(String inputField74) {
        this.inputField74 = inputField74;
    }

    public String getInputField75() {
        return inputField75;
    }

    public void setInputField75(String inputField75) {
        this.inputField75 = inputField75;
    }

    public String getInputField76() {
        return inputField76;
    }

    public void setInputField76(String inputField76) {
        this.inputField76 = inputField76;
    }

    public String getInputField77() {
        return inputField77;
    }

    public void setInputField77(String inputField77) {
        this.inputField77 = inputField77;
    }

    public String getInputField78() {
        return inputField78;
    }

    public void setInputField78(String inputField78) {
        this.inputField78 = inputField78;
    }

    public String getInputField79() {
        return inputField79;
    }

    public void setInputField79(String inputField79) {
        this.inputField79 = inputField79;
    }

    public String getInputField80() {
        return inputField80;
    }

    public void setInputField80(String inputField80) {
        this.inputField80 = inputField80;
    }

    public String getInputField81() {
        return inputField81;
    }

    public void setInputField81(String inputField81) {
        this.inputField81 = inputField81;
    }

    public String getInputField82() {
        return inputField82;
    }

    public void setInputField82(String inputField82) {
        this.inputField82 = inputField82;
    }

    public String getInputField83() {
        return inputField83;
    }

    public void setInputField83(String inputField83) {
        this.inputField83 = inputField83;
    }

    public String getInputField84() {
        return inputField84;
    }

    public void setInputField84(String inputField84) {
        this.inputField84 = inputField84;
    }

    public String getInputField85() {
        return inputField85;
    }

    public void setInputField85(String inputField85) {
        this.inputField85 = inputField85;
    }

    public String getInputField86() {
        return inputField86;
    }

    public void setInputField86(String inputField86) {
        this.inputField86 = inputField86;
    }

    public String getInputField87() {
        return inputField87;
    }

    public void setInputField87(String inputField87) {
        this.inputField87 = inputField87;
    }

    public String getInputField88() {
        return inputField88;
    }

    public void setInputField88(String inputField88) {
        this.inputField88 = inputField88;
    }

    public String getInputField89() {
        return inputField89;
    }

    public void setInputField89(String inputField89) {
        this.inputField89 = inputField89;
    }

    public String getInputField90() {
        return inputField90;
    }

    public void setInputField90(String inputField90) {
        this.inputField90 = inputField90;
    }

    public String getInputField91() {
        return inputField91;
    }

    public void setInputField91(String inputField91) {
        this.inputField91 = inputField91;
    }

    public String getInputField92() {
        return inputField92;
    }

    public void setInputField92(String inputField92) {
        this.inputField92 = inputField92;
    }

    public String getInputField93() {
        return inputField93;
    }

    public void setInputField93(String inputField93) {
        this.inputField93 = inputField93;
    }

    public String getInputField94() {
        return inputField94;
    }

    public void setInputField94(String inputField94) {
        this.inputField94 = inputField94;
    }

    public String getInputField95() {
        return inputField95;
    }

    public void setInputField95(String inputField95) {
        this.inputField95 = inputField95;
    }

    public String getInputField96() {
        return inputField96;
    }

    public void setInputField96(String inputField96) {
        this.inputField96 = inputField96;
    }

    public String getInputField97() {
        return inputField97;
    }

    public void setInputField97(String inputField97) {
        this.inputField97 = inputField97;
    }

    public String getInputField98() {
        return inputField98;
    }

    public void setInputField98(String inputField98) {
        this.inputField98 = inputField98;
    }

    public String getInputField99() {
        return inputField99;
    }

    public void setInputField99(String inputField99) {
        this.inputField99 = inputField99;
    }

    public String getInputField21() {
        return inputField21;
    }

    public void setInputField21(String inputField21) {
        this.inputField21 = inputField21;
    }

    public String getInputField22() {
        return inputField22;
    }

    public void setInputField22(String inputField22) {
        this.inputField22 = inputField22;
    }

    public String getInputField23() {
        return inputField23;
    }

    public void setInputField23(String inputField23) {
        this.inputField23 = inputField23;
    }

    public String getInputField24() {
        return inputField24;
    }

    public void setInputField24(String inputField24) {
        this.inputField24 = inputField24;
    }

    public String getInputField25() {
        return inputField25;
    }

    public void setInputField25(String inputField25) {
        this.inputField25 = inputField25;
    }

    public String getInputField26() {
        return inputField26;
    }

    public void setInputField26(String inputField26) {
        this.inputField26 = inputField26;
    }

    public String getInputField27() {
        return inputField27;
    }

    public void setInputField27(String inputField27) {
        this.inputField27 = inputField27;
    }

    public String getInputField28() {
        return inputField28;
    }

    public void setInputField28(String inputField28) {
        this.inputField28 = inputField28;
    }

    public String getInputField29() {
        return inputField29;
    }

    public void setInputField29(String inputField29) {
        this.inputField29 = inputField29;
    }

    public String getInputField30() {
        return inputField30;
    }

    public void setInputField30(String inputField30) {
        this.inputField30 = inputField30;
    }

    public String getInputField1() {
        return inputField1;
    }

    public void setInputField1(String inputField1) {
        this.inputField1 = inputField1;
    }

    public String getInputField2() {
        return inputField2;
    }

    public void setInputField2(String inputField2) {
        this.inputField2 = inputField2;
    }

    public String getInputField3() {
        return inputField3;
    }

    public void setInputField3(String inputField3) {
        this.inputField3 = inputField3;
    }

    public String getInputField4() {
        return inputField4;
    }

    public void setInputField4(String inputField4) {
        this.inputField4 = inputField4;
    }

    public String getInputField5() {
        return inputField5;
    }

    public void setInputField5(String inputField5) {
        this.inputField5 = inputField5;
    }

    public String getInputField6() {
        return inputField6;
    }

    public void setInputField6(String inputField6) {
        this.inputField6 = inputField6;
    }

    public String getInputField7() {
        return inputField7;
    }

    public void setInputField7(String inputField7) {
        this.inputField7 = inputField7;
    }

    public String getInputField8() {
        return inputField8;
    }

    public void setInputField8(String inputField8) {
        this.inputField8 = inputField8;
    }

    public String getInputField9() {
        return inputField9;
    }

    public void setInputField9(String inputField9) {
        this.inputField9 = inputField9;
    }

    public String getInputField10() {
        return inputField10;
    }

    public void setInputField10(String inputField10) {
        this.inputField10 = inputField10;
    }

    public String getInputField11() {
        return inputField11;
    }

    public void setInputField11(String inputField11) {
        this.inputField11 = inputField11;
    }

    public String getInputField12() {
        return inputField12;
    }

    public void setInputField12(String inputField12) {
        this.inputField12 = inputField12;
    }

    public String getInputField13() {
        return inputField13;
    }

    public void setInputField13(String inputField13) {
        this.inputField13 = inputField13;
    }

    public String getInputField14() {
        return inputField14;
    }

    public void setInputField14(String inputField14) {
        this.inputField14 = inputField14;
    }

    public String getInputField15() {
        return inputField15;
    }

    public void setInputField15(String inputField15) {
        this.inputField15 = inputField15;
    }

    public String getInputField16() {
        return inputField16;
    }

    public void setInputField16(String inputField16) {
        this.inputField16 = inputField16;
    }

    public String getInputField17() {
        return inputField17;
    }

    public void setInputField17(String inputField17) {
        this.inputField17 = inputField17;
    }

    public String getInputField18() {
        return inputField18;
    }

    public void setInputField18(String inputField18) {
        this.inputField18 = inputField18;
    }

    public String getInputField19() {
        return inputField19;
    }

    public void setInputField19(String inputField19) {
        this.inputField19 = inputField19;
    }

    public String getInputField20() {
        return inputField20;
    }

    public void setInputField20(String inputField20) {
        this.inputField20 = inputField20;
    }
}

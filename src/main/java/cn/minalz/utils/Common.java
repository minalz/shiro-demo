package cn.minalz.utils;

import org.springframework.util.DigestUtils;

public class Common {


    // 主机干粉物料组

    public static final String ZJGF = "2000";
    // 果肉物料组
    public static final String GR = "2004";
    // 果汁物料组
    public static final String GZ = "2001";
    // 瓶胚物料组
    public static final String PP = "3003";
    // 原材料通用物料组
    public static final String YCLTY = "0000";
    // 包材物料组
    public static final String BC = "";
    // 物料编码--白糖
    public static final String WLBM_BT = "300001";
    // 果汁果肉kit物料组
    public static final String MATKL_KIT_GZ_GR = "2007";
    // 液糖物料组
    public static final String MATKL_1001 = "1001";
    public static final String ZJTLKZ = "ZJTLKZ";


    // 用于交货单明细中的创建铁笼
    public static final String PP_LIFNR = "YYYY";

    // 转储采购订单类型
    public static final String[] ZC_BSART = { "UB", "ZNB9" };
    // 生产订单锁定状态标识
    public static final String PRODUCTION_ORDER_LOCKED = "X";

    // 交货单SAP完成标志
    public static final String DO_WBSTK = "C";

    // 其他按托
    public static final String[] OT_MATKL = { "2002", "2003", "2005" };
    /**
     * 工厂Type
     */
    public static final String WERKS = "WERKS";

    /**
     * 移动Type
     */
    public static final String ACTYP = "ACTYP";

    // 生产商Type
    public static final String PRODUCT = "PRODUCER";

    // 通用编码配置表中物料组的type名称
    public static final String CURRENCY_MATKL = "MATKL";

    // 通用编码配置表中库存地点的type名称
    public static final String CURRENCY_LGORT = "LGORT";

    // 库位地址
    public static final String STLOT = "1011";
    public static final String STLOT_1011 = "1011"; //1011仓位
    public static final String STLOT_1001 = "1001"; //1001仓位
    public static final String STLOT_1011_TYPE = "非寄售"; //1011仓位的物料类型
    public static final String STLOT_1001_TYPE = "寄售"; //1001仓位的物料类型

    // SAP接口返回类型，S为成功，其他为失败
    public static final String SAP_TYPE_SUCCESS = "S";

    // SAP地址
//    public static final String SAP_URL = "http://svr09185.scmc.com:8010/sap/zkafka_message?sap-client=805";
//    public static final String SAP_URL = "http://svr09082.scmc.com:8000/sap/zkafka_message?sap-client=300";

    // QMS地址
    public static final String QMS_URL = "";

    /**
     * 物料组Type
     */
    public static final String MATKL = "MATKL";

    /**
     * 发料的actype
     */
    public static final String FL = "FL";
    // 线边库确认
    public static final String XBC = "XBC";
    /**
     * 发料需求
     */
    public static final String DEM = "DEM";
    // 先过期先出
    public static final String XDXC = "XDXC";
    // 先进先出
    public static final String XJXC = "XJXC";
    //果汁果肉
    public static final String GZGR = "GZGR";
    //主剂干粉
    public static final String ZJ = "ZJGF";
    //车间发料
    public static final String CJFL = "CJFL";
    public static final String MOVE = "MOVE";
    public static final String XTY = "XTY";
    public static final String YTX = "YTX";
    public static final String XTX = "XTX";
    //库存调整
    public static final String KCTZ = "KCTZ";
    //果汁投料
    public static final String GZTL = "GZTL";
    //果肉投料
    public static final String GRTL = "GRTL";
    //瓶胚收货
    public static final String PPSH = "PPSH";
    //库存状态
    public static final String HUSTATUS = "HUSTATUS";
    //辅料物料组
    public static final String FLMATKL = "FLMATKL";
    //辅料工厂
    public static final String FLWERKS = "FLWERKS";
    public static final String CPMATKL = "CPMATKL";
    //寄售交货单标志
    public static final String ZJS = "ZJS";
    //发料需求单的物料组
    public static final String FLXQ = "FLXQ";
    public static final String BZCL = "BZCL";
    //Bom清单可以创建发料需求单的物料组
    public static final String BOMFL = "BOMFL";

    public static final String YTGT = "YTGT";
    //虚拟物料
    public static final String XNWL = "XNWL";
    public static final String GQSJ = "GQSJ";


    public static final String PO = "PO";
    public static final String RETURN = "RETURN";

    public static final String HEBING = "HEBING"; //合并托盘
    public static final String H_I = "H_I"; //合入
    public static final String H_O = "H_O"; //合出

    public static final String XTPZ = "XTPZ"; //系统配置
    public static final String TLCL = "TLCL"; //系统配置
    public static final String GZGRTK = "GZGRTK"; //果汁果肉二次回库保值天数
    public static final String PACKET_EX = "PACKET_EX"; //小包果汁二次回库保值天数


    /**
     * 错误Code
     */
    public static final String ERROR_CODE = "01";
    /**
     * 发料模块消息提示
     */
    public static final String ZJGF_SUBMIT_NUMBER_ERROR = "主剂干粉请勿超过需求量";
    public static final String ZJGF_SUBMIT_CHARG_ERROR = "主剂干粉请按推荐批次发料";

    /**
     * 投料超过百分比错误
     */
    public static final String TL_BF_ERROR = "该订单已满足投料量,不能继续投料";


    // 大桶果汁果肉创建批次的id
    public static final int chargId = 1;

    // 需求单流水号id
    public static final int demandId = 2;


    // 是否启用 0：停用，1：启用
    public static final String ACTIVE_0 = "0";
    public static final String ACTIVE_1 = "1";

    // 仓位类型，是否线边库 0：原材料库，1：线边库
    public static final Integer SUBLT_STATUS_0 = 0;
    public static final Integer SUBLT_STATUS_1 = 1;

    // 移库单明细状态0:未完成 1：已处理 2：已完成
    public static final Integer DEMAND_DETAIL_STATUS_0 = 0;
    public static final Integer DEMAND_DETAIL_STATUS_1 = 1;
    public static final Integer DEMAND_DETAIL_STATUS_2 = 2;

    // 货物移动状态 A:未完成 C：已完成
    public static final String WBSTK_A = "A";
    public static final String WBSTK_C = "C";

    // 托盘状态0:在库 1：需求单绑定线 边库未确定 2：质检
    public static final String HUMAT_STATS_0 = "0";
    public static final String HUMAT_STATS_1 = "1";
    public static final String HUMAT_STATS_2 = "2";

    // 托盘类型 0:装运单 1：交货单 2：采购订单
    public static final Integer HUACT_TYPE_0 = 0;
    public static final Integer HUACT_TYPE_1 = 1;
    public static final Integer HUACT_TYPE_2 = 2;

    // asn类型 0：主剂asn 1：供应商asn 2：拆托 3:瓶胚虚拟托盘
    public static final Integer INVST_TYPE_0 = 0;
    public static final Integer INVST_TYPE_1 = 1;
    public static final Integer INVST_TYPE_2 = 2;
    public static final Integer INVST_TYPE_3 = 3;

    // 是否自建批次0：否 1：是
    public static final Integer BATCH_MATERIAL_ISNEW_0 = 0;
    public static final Integer BATCH_MATERIAL_ISNEW_1 = 1;

    // 需求单操作类型常量
    public static final String ACTYP_321 = "321"; // 质检到非限制
    public static final String ACTYP_322 = "322"; // 非限制到质检
    public static final String ACTYP_344 = "344"; // 非限制到冻结
    public static final String ACTYP_CHAI = "CHAI"; // 拆托
    public static final String ACTYP_CHAI_I = "C_I"; //拆入
    public static final String ACTYP_CHAI_O = "C_O"; //拆出
    public static final String ACTYP_FL = "FL"; // 发料需求
    public static final String ACTYP_RUKU = "ruku"; // 入库
    public static final String QC = "QC"; // 切换入库
    public static final String ACTYP_TL = "TL"; // 投料
    public static final String ACTYP_QB = "QB"; // 重包订单
    public static final String ACTYP_XBC = "XBC"; // 线边库确认
    public static final String ACTYP_TUI = "TUI"; // 退料
    public static final String ACTYP_SCTL = "SCTL"; // 生产退料
    public static final String ACTYP_ZCFH = "ZCFH"; // 出库
    public static final String ACTYP_XSFH = "XSFH"; // 销售出库
    public static final String ACTYP_JSTH = "JSTH"; // 销售退库
    //日志expvalue1
    public static final String EXPVALUE1_DUCTION = "duction"; // 直发扣减


    // 需求单来源类型常量
    public static final String REFTP_CHAI = "CHAI"; // 拆托
    public static final String REFTP_DEM = "DEM"; // 发料需求
    public static final String REFTP_FEED = "FEED"; // 投料
    public static final String REFTP_TL = "TL"; // 投料
    public static final String REFTP_KCTZ = "KCTZ"; // 库存调整，质检，冻结
    public static final String REFTP_SHIP = "ship"; // 装运单
    public static final String REFTP_SCTL = "SCTL"; // 生产退料
    public static final String REFTP_CHUKU = "CHUKU"; // 转储发货
    public static final String REFTP_JSCK = "JSCK"; // 寄售出库


    // 以下为异常信息常量
    public static final String DATA_EXIST = "数据已存在";
    public static final String PARAM_ERROR = "参数有误";
    public static final String DATE_ERROR = "日期格式有误";
    public static final String OPERATION_FAILED = "操作失败";
    public static final String OPERATION_REPEAD_ERROR = "重复操作";
    public static final String EXIDV_NOT_FOUND = "未找到托盘信息";
    public static final String SUBLT_NOT_FOUND = "未找到仓位信息";
    public static final String ORDER_DETAIL_NOT_FOUND = "未找到仓位信息";
    public static final String PRODUCTION_ORDER_ERROR = "生产订单不可调整";
    public static final String PRODUCTION_ORDER_FAILED = "库存数量不足，调整失败";

    // 创批，主剂，果汁果肉对应通用编码配置表
    public static final String BATCH_TYPE = "BATCH";
    // 主剂干粉
    public static final String BATCH_SCKEY_LIFNR = "lifnr";
    public static final String BATCH_SCKEY_ZBATCH01 = "zbatch01";
    public static final String BATCH_SCKEY_ZBATCH02 = "ZBATCH02";
    public static final String BATCH_SCKEY_ZBATCH03 = "ZBATCH03";
    public static final String BATCH_SCKEY_ZBATCH04 = "zbatch04";
    public static final String BATCH_SCKEY_ZBATCH06 = "zbatch06";
    public static final String BATCH_SCKEY_ZBATCH08 = "zbatch08";
    public static final String BATCH_SCKEY_ZBATCH09 = "zbatch09";
    // 果汁果肉
    public static final String BATCH_SCKEY_GR_LIFNR = "grlifnr";
    public static final String BATCH_SCKEY_GR_ZBATCH01 = "grzbatch01";
    public static final String BATCH_SCKEY_GR_ZBATCH02 = "grzbatch02";
    public static final String BATCH_SCKEY_GR_ZBATCH03 = "grzbatch03";
    public static final String BATCH_SCKEY_GR_ZBATCH04 = "grzbatch04";
    public static final String BATCH_SCKEY_GR_ZBATCH06 = "grzbatch06";
    public static final String BATCH_SCKEY_GR_ZBATCH08 = "grzbatch08";
    public static final String BATCH_SCKEY_GR_ZBATCH09 = "grzbatch09";

    //罐号Type
    public static final String POT_TYPE = "POTEXIDV";

    //果汁果肉TYPE
    public static final String GZGR_TYPE = "GZGR";
    //主剂干粉TYPE
    public static final String ZJGF_TYPE = "ZJGF";
    //生产退料TYPE
    public static final String SCTL_TYPE = "SCTL";
    //供应商TYPE
    public static final String LIFNR_TYPE = "LIFNR";
    //版本号type
    public static final String VERSION_TYPE = "PDAVERSION";
    //创批下拉框
    public static final String BATCHCREATE ="BATCHCREATE";
    //制造商下拉框
    public static final String PRODUCER ="PRODUCER";
    //品牌/模具号下拉
    public static final String ZBATCH03 ="ZBATCH03";
    //配方代码
    public static final String ZBATCH09 ="ZBATCH09";
    public static final String BD = "BD";
    public static final String PRINT = "PRINT";
    //按托投料TYPE
    public static final String ATTL_TYPE = "ATTL";

    //瓶胚投料TYPE
    public static final String PPTL_TYPE = "PPTL";

    public static final String MAILHEAD_TYPE = "MAILHEAD";
    public static final String MAILHEAD_SCKEY = "LQTX";
    public static final String MAILBODY_TYPE = "MAILBODY";
    public static final String MAILBODY_SCKEY = "LQTX";
    public static final String MAIL_TYPE = "MAIL";
    public static final String ENTER_MAIL_TYPE = "ENTERMAIL";
    //临期提醒
    public static final String LQTX = "LQTX";

    //-------------------看板----------------------

    public static final String TYEP_01 = "01"; //进口果肉
    public static final String TYEP_02 = "02"; //进口小包
    public static final String TYEP_03 = "03"; //国产果肉
    public static final String KBBJSJ = "KBBJSJ"; //看板报警时间
    public static final String GZJD = "GZJD"; //果汁的解冻时间
    public static final String TFWL = "TFWL"; //糖房物料

    public static final String ISWARN = "1"; //果汁报警,按物料维度
    public static final String KBJDQY = "KBJDQY"; //解冻区域
    public static final String KBJDQY_NORMAL = "NORMAL"; //无配置解冻区域的通用解冻区域


    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";
    public static String getMD5(String str) {
        String base = str +"/"+slat;
        String md5 = base;
        for(int i=0;i<10;i++){
            md5 = DigestUtils.md5DigestAsHex(md5.getBytes());
        }
        return md5;
    }

    public static final String LQWL_BASE = "/opt/scmciwh/lqwl/";
    public static final String MAIL_BASE = "/opt/scmciwh/mailexcel/"; //邮件发送暂存地址
    public static final String MAIL_ENTER = "ENTER"; //入库邮件名称
    public static final Integer LQWL_DAY = 30;//用于数据库没维护临期提醒日期，系统默认有个提醒日期

    //仓储发送QMS的操作类型
    public static final String QMS_TYPE_STORAGE = "入库";
    public static final String QMS_TYPE_FEED = "投料";
    public static final String QMS_TYPE_DISPATCH = "仓库到线边库";
    public static final String QMS_TYPE_COMFIRM = "线边库确认";
    public static final String QMS_TYPE_RETURN = "退库";
    public static final String QMS_TYPE_PRODUTION_RETURN = "生产退料";
    public static final String QMS_TYPE_FALSE_COLLAR = "假领";
    public static final String QMS_TYPE_FALSE_RETURN = "假退";
    //QMS的optype
    public static final String QMSFLSEND = "QMSFLSend"; //发料的optype
    public static final String QMSRTSEND = "QMSRTSend"; //退料的optype
    public static final String QMSCFSEND = "QMSCFSend"; //线边库确认的optype

    //太古
    public static final String Z_AUFNR = "T999999"; //太古的生产订单
    public static final String Z_CIP = "FSP399999999999"; //太古的生产订单
    public static final String UPDATE_ACTYPE = "update"; //太古表修改type
    public static final String UPDATE_ADD = "add"; //太古表新增
    public static final String UPDATE_DELETE = "delete"; //太古表删除
    public static final String UPDATE_MOVE = "move"; //太古表修改仓位

    //销售标识
    public static final String ZP01 = "ZP01"; //装运单销售标识

    //物料组
    public static final String MATKL_1000 = "1000"; //液化糖
    public static final String MATKL_1002 = "1002"; //果糖
    public static final String MATKL_2001 = "2001"; //果汁
    public static final String MATKL_2004 = "2004"; //果肉
    public static final String MATKL_2000 = "2000"; //主剂
    public static final String MATKL_3003 = "3003"; //瓶胚
    public static final String MATKL_3004 = "3004"; //瓶胚
    public static final String MATKL_3005 = "3005"; //标签
    public static final String MATKL_3006 = "3006"; //瓶盖
    public static final String MATKL_3007 = "3007"; //瓶盖
    public static final String MATKL_3008 = "3008"; //瓶盖
    public static final String MATKL_4001 = "4001"; //纸箱
    public static final String MATKL_4004 = "4004"; //条形码
    public static final String MATKL_4003 = "4003"; //PE膜

    //收获率
    public static final Double U = 2000000.0; //容量
    public static final String ZDMATNR = "ZDMATNR"; //纸垫物料组
    public static final String ZXMATNR = "ZXMATNR"; //纸箱物料组
    public static final String PECM = "PECM"; //PE彩膜
    public static final String PEFM = "PEFM"; //PE防尘膜

    //寄售单类型
    public static final String TYPE_JS_01="01"; //寄售入库
    public static final String TYPE_JS_02="02"; //寄售退货
    //转储发货和寄售出库的类型
    public static final String TYPE_LF="LF"; //销售出库
    public static final String TYPE_NL="NL"; //转储发货
    //Excel后缀
    public static final String SUFFIX_2003 = ".xls";
    public static final String SUFFIX_2007 = ".xlsx";

    public static final String BCTLSLKZ = "BCTLSLKZ"; //包材投料数量控制
    public static final String PPRKSLKZ = "PPRKSLKZ"; //瓶胚入库数量控制

    //公共异常
    public static final String NORMAL_ERROR = "服务异常,请联系管理员"; //PE防尘膜

    //小包果汁果肉条码投料
    public static final String XBGZGR = "XBGZGR"; //小包果汁果肉条码投料

    //融糖使用静态常量
    public static final String REFTP_MEIL = "MEIL"; //融糖功能日志标识
    public static final String ACTYP_CREATE = "CREATE"; //创建
    public static final String ACTYP_DELETE = "DELETE"; //删除

    public static final String LGNUM = "LGNUM"; //仓库
    public static final String ROUTE = "ROUTE"; //路线

    public static final String CARCHECK = "CARCHECK"; //车辆检查
    public static final String OUTWARD = "OUTWARD"; //外观检查
    public static final String MEINS_KG = "KG"; //单位:KG

    //包材FEFO
    public static final String BCFEFO = "BCFEFO";
    public static final String SECOND_CHECK = "SECONDCHECK"; // 包材fefo跳过一层审核
    public static final String SLICE = "切片"; // 用来分割切片名称和code

    public static final String CAGE_MAKTX = "%铁笼%"; //铁笼中文描述

    /** 通用常数 TRUE */
    public static final Boolean COMMON_TRUE = true; 

    /** 通用常数 FALSE */
    public static final Boolean COMMON_FALSE = false;
}

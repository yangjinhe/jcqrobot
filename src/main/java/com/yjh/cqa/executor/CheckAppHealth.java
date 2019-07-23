package com.yjh.cqa.executor;

import com.sobte.cqp.jcq.entity.CoolQ;
import com.yjh.cqa.util.CheckAppHealthUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckAppHealth implements Runnable {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);

    private CoolQ CQ;

    private static List<UrlVo> urlVoList = new ArrayList<UrlVo>();

    public CheckAppHealth(CoolQ CQ) {
        this.CQ = CQ;
    }

    static {
        urlVoList.add(new UrlVo("http://10.183.7.25/efmp-common-web/pub/qryArea", "{\"level\":\"1\"}"));
        urlVoList.add(new UrlVo("http://10.183.7.25/efmp-activity-web/activity/queryStateDict", ""));
        urlVoList.add(new UrlVo("http://10.183.7.25/efmp-merchant-web/merchant/qryMerchantPage", "{\"merchantCode\":\"123\",\"merchantName\":\"\",\"areaId\":\"\",\"merchantStateCd\":\"\",\"curPage\":1,\"pageSize\":1}"));
        urlVoList.add(new UrlVo("http://10.183.7.25/efmp-resource-web/rsc/qryRscExt", "{\"pageSize\":1,\"curPage\":1,\"rscStateCd\":1,\"rscCode\":\"123\"}"));
        urlVoList.add(new UrlVo("http://10.183.7.25/efmp-maintain-web/maintain/queryDownloadReqList", "{\"curPage\":1,\"pageSize\":1,\"downloadType\":\"1\"}"));
        urlVoList.add(new UrlVo("http://10.183.7.25/psm/privilege/profile/queryOperationSpec.action", "{\"requirePaging\":true,\"currentPage\":1,\"rowNumPerPage\":1,\"operationSpecCd\":\"123\"}"));
    }

    static class UrlVo {
        private String url;
        private String param;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public UrlVo(String url, String param) {
            this.url = url;
            this.param = param;
        }
    }


    @Override
    public void run() {
        for (UrlVo urlVo : urlVoList) {
            try {
                long st = System.currentTimeMillis();
                String check = CheckAppHealthUtil.check(urlVo.getUrl(), urlVo.getParam());
                long et = System.currentTimeMillis();
                long timeout = et - st;
                if ("0".equals(check)) {
                    if (timeout > 3000) {
                        CQ.logDebug("Warning", "耗时有点久" + check);
                        CQ.sendPrivateMsg(1846253361L, urlVo.getUrl() + "超过3秒返回:" + timeout + ":" + check);
                    } else {
                        CQ.logDebug("success", check);
                    }
                } else {
                    CQ.logDebug("业务错误", check);
                    CQ.sendPrivateMsg(1846253361L, check);
                }
            } catch (IOException e) {
                CQ.logDebug("exception", e.getMessage());
            }
        }
    }
}
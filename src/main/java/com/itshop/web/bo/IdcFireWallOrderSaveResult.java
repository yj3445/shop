package com.itshop.web.bo;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.itshop.web.dto.request.IdcFireWallOrderSaveParam;
import com.itshop.web.util.ListUtil;
import lombok.Data;

import java.util.List;

@Data
public class IdcFireWallOrderSaveResult extends OrderSaveResult {

    /**
     * 上(下)行协议端口信息
     */
    @Data
    public static class InOrOutProtocolPortInfo {
        /**
         * 端口
         */
        private List<Integer> port;

        /**
         * 上行/下行
         * 0-out-上行(内部访问外部);
         * 1-in-下行(外部访问内部)
         */
        private Integer inOrOut;

        /**
         * 协议类型
         * (Any,TCP,UDP,PPP)
         */
        private String protocol;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InOrOutProtocolPortInfo portInfo = (InOrOutProtocolPortInfo) o;
            return ListUtil.equals(port, portInfo.port)
                    && Objects.equal(inOrOut, portInfo.inOrOut)
                    && Objects.equal(protocol, portInfo.protocol);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(port, inOrOut, protocol);
        }
    }

    private Boolean oldOpen;
    private List<InOrOutProtocolPortInfo> oldEnableInPorts;
    private List<InOrOutProtocolPortInfo> oldDisableOutPorts;

    private Boolean newOpen;
    List<InOrOutProtocolPortInfo> newEnableInPorts;
    List<InOrOutProtocolPortInfo> newDisableOutPorts;
}

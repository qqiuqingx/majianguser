package com.majiang.user.majianguser.service;

import com.alipay.api.AlipayApiException;
import com.majiang.user.majianguser.bean.vo.MajiangVo;

import javax.servlet.http.Cookie;

public interface AlipayService {

    /**
     *
     * @param majiangKeyID 麻将的KeyID
     * @param token token
     * @return
     * @throws Exception
     */
    String webPagePay( Integer majiangKeyID, String token) throws Exception;

    /**
     * 退款
     * @param OrderID    订单编号
     * @param refundReason  退款原因
     * @param refundAmount  退款金额
     * @param outRequestNo  标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     */
    String refund(String OrderID,String refundReason,Integer refundAmount,String outRequestNo) throws AlipayApiException;

    /**
     * 交易查询
     * @param OrderID 订单编号（唯一）
     */
    String query(String OrderID) throws AlipayApiException;

    /**
     * 交易关闭
     * @param OrderID （唯一）
     */
    String close(String OrderID) throws AlipayApiException;

    /**
     * 退款查询
     * @param OrderID 订单编号（唯一）
     * @param outRequestNo 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     */
    String refundQuery(String OrderID,String outRequestNo) throws AlipayApiException;


}

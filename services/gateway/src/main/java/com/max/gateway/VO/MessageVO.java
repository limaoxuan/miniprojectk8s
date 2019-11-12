package com.max.gateway.VO;

public class MessageVO {
    public static ResultVO<String> normalReturn(String message) {
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(1);
        resultVO.setMsg("fail");
        resultVO.setData(message);
        return resultVO;
    }
}

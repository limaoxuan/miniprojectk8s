package com.max.product.VO;

public class ResultMessage {
    public static ResultVO<String> normalReturn() {
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData("{}");
        return resultVO;
    }

    public static ResultVO<String> normalReturn(String message) {
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(1);
        resultVO.setMsg("fail");
        resultVO.setData(message);
        return resultVO;
    }
}

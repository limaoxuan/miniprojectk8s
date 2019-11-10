package com.max.account.VO;

public  class ResultMessage {

    public static ResultVO<String> normalReturn() {
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData("{}");
        return resultVO;
    }
}

package com.scaffold.file.decode.handler;

import com.scaffold.file.decode.model.DecodeModel;

import java.util.stream.Stream;

public class ExcelDecodeHandler implements  DecodeHandler  {



    @Override
    public DecodeModel decode(String body, DecodeModel decodeModel) {
        decodeModel.setBody(body);
        decodeModel.setT(analysis(body));
        return decodeModel;
    }

    private Object analysis(String body){

        Stream.of(body);


        return null;
    }
}

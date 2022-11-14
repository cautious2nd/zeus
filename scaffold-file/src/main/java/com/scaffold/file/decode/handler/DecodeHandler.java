package com.scaffold.file.decode.handler;

import com.scaffold.file.decode.model.DecodeModel;

public interface DecodeHandler {
    public DecodeModel decode(String body, DecodeModel decodeModel);
}

package com.hyperionml;// Copyright (c) Alibaba, Inc. and its affiliates.

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;

import java.util.Scanner;


public class Main {
    public static void callWithMessage(String msg)
            throws NoApiKeyException, ApiException, InputRequiredException {
        Constants.apiKey="sk-lFPev8S8qK";

        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful assistant.").build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(msg).build();
        msgManager.add(systemMsg);
        msgManager.add(userMsg);
        QwenParam param =
                QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get())
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .build();
        GenerationResult result = gen.call(param);
        result.getOutput().getChoices().iterator().forEachRemaining(choice -> System.out.println(choice.getMessage().getContent()));
    }


    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        while (true){
            System.out.println("输入问题：");
            String msg = input.nextLine();
            if(!"/exit".equals(msg)){
                try {
                    callWithMessage(msg);
                } catch (ApiException | NoApiKeyException | InputRequiredException e) {
                    System.out.println(e.getMessage());
                }
            }else {
                break;
            }
        }
    }
}
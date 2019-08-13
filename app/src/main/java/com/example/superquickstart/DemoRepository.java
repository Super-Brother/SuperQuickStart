package com.example.superquickstart;

import com.wenchao.common.base.BaseModel;

/**
 * @author wenchao
 * Date 2019/8/13 17:14
 * Description
 */
public class DemoRepository extends BaseModel {

    private volatile static DemoRepository INSTANCE = null;

    private DemoRepository() {
    }

    public static DemoRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (DemoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DemoRepository();
                }
            }
        }
        return INSTANCE;
    }
}

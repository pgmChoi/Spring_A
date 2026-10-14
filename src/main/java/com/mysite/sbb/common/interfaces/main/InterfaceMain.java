package com.mysite.sbb.common.interfaces.main;
import com.mysite.sbb.common.interfaces.code.CarMaker;
import com.mysite.sbb.common.interfaces.code.HyundaiMaker;
import com.mysite.sbb.common.interfaces.code.KiaMaker;
import com.mysite.sbb.common.interfaces.code.OrderManager;

public class InterfaceMain {
    public static void main(String[] args) {
        CarMaker maker = new KiaMaker();
        OrderManager orderManager = new OrderManager(maker);
        //orderManager.setMaker(maker);
        orderManager.order();
    }
}

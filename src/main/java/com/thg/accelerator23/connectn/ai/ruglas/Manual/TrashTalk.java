package com.thg.accelerator23.connectn.ai.ruglas.Manual;

public class TrashTalk {

    static String[] trash = {"You do realise we're trying to connect FOUR right ???",
            "Oooo... not sure about that move mate.",
            "You'd struggle to pour water out of a boot with instructions on the heel",
            ""
    };

    public static void talkTrash() {
        int rand = (int) Math.floor(Math.random()*trash.length);
        System.out.println(trash[rand]);
    }
}

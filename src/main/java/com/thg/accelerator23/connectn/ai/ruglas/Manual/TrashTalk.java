package com.thg.accelerator23.connectn.ai.ruglas.Manual;

public class TrashTalk {

    static String[] trash = {"You do realise we're trying to connect FOUR right ???",
            "Oooo... not sure about that move mate.",
            "You'd struggle to pour water out of a boot with instructions on the heel.",
            "Did you set your minimax depth to 0 or something ?",
            "Did you get your AI from Wish ? ",
            "When mum says 'we've got an AI at home' she means yours.",
            ""
    };

    public static void talkTrash() {
        int rand = (int) Math.floor(Math.random()*trash.length);
        System.out.println(trash[rand]);
    }
}

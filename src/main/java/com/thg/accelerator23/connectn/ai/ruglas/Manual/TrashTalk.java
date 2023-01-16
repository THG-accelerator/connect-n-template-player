package com.thg.accelerator23.connectn.ai.ruglas.Manual;

public class TrashTalk {

    static String[] trash = {"You do realise we're trying to connect FOUR right ???",
            "Oooo... not sure about that move mate.",
            "You'd struggle to pour water out of a boot with instructions on the heel.",
            "Did you set your minimax depth to 0 or something ?",
            "Did you get your AI from Wish ? ",
            "When mum says 'we've got an AI at home' she means yours.",
            "It's cute that you tried but embarrassing",
            "I'd beat thee, but I would infect my hands.",
            "Thou sodden-witted lord! Thou hast no more brain than I have in mine elbows ",
            "Thine face is not worth sunburning." ,
            "Thy tongue outvenoms all the worms of Nile.",
            "Thy bot is as dry as the remainder biscuit after voyage.",
            "Heaven truly knows that thou art false as hell",
            "Thine algorithm truly is clapped.",
            "Shall I compare thee to a rainy day in Manchester ?",
            "Get to the bottom of the stack, and there remain.",
            "Go home algorithm, you're drunk.",
            "Yer a goon pal"

    };

    public static void talkTrash() {
        int rand = (int) Math.floor(Math.random()*trash.length);
        System.out.println(trash[rand]);
    }
}

package io.github.vialdevelopment.tori.client.commands;

import io.github.vialdevelopment.tori.api.runnable.command.Command;
import io.github.vialdevelopment.tori.util.Logger;
import io.github.vialdevelopment.tori.util.SessionUtil;
import net.minecraft.client.util.Session;
import net.minecraft.util.Formatting;

public class LoginCommand extends Command {
    public LoginCommand() {
        super("Login", "Lets you login to an account");
    }


    @Override
    public void run(String[] args) {
        super.run(args);
        if (args.length == 2) {
            try {
                new Thread(() -> {
                    final Session auth = SessionUtil.createSession(args[0], args[1]);
                    if (auth == null) {
                        Logger.log(Formatting.RED + "Login failed!");
                    } else {
                        Logger.log(Formatting.GREEN + "Logged in. (" + auth.getUsername() + ")");
                        SessionUtil.setSession(auth);
                    }
                }).start();
            } catch (Exception e) {
                Logger.log("Login threw exception");
                e.printStackTrace();
            }
        }
    }
}

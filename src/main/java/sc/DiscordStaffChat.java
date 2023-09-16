package sc;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DiscordStaffChat implements me.petterim1.discordchat.DiscordChatReceiver {

    private final StaffChat main;

    public DiscordStaffChat(StaffChat main) {
        this.main = main;

        me.petterim1.discordchat.API.registerReceiver(this);

        main.getLogger().info("DiscordChat support enabled");
    }

    @Override
    public void receive(GuildMessageReceivedEvent e) {
        if (e.getAuthor().isBot() || !e.getChannel().getId().equals(main.cfg.getString("discord-staff-channel-id"))) {
            return;
        }

        String message = TextFormat.clean(e.getMessage().getContentStripped());
        if (message.trim().isEmpty()) {
            return;
        }

        String msg = main.cfg.getString("format").replace("%name%", e.getMember() == null ? e.getAuthor().getName() : e.getMember().getEffectiveName()).replace("%message%", message);

        for (Player p : main.getServer().getOnlinePlayers().values()) {
            if (p.hasPermission("staff.chat")) {
                p.sendMessage(msg);
            }
        }
    }
}

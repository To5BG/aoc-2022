import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        System.out.print(intros[new Random().nextInt(intros.length)]);
        while (true) {
            System.out.print("Choose a day:");
            int inp = scr.nextInt();
            if (inp <= 0 || inp > 25) break;
            try {
                String input = Files.readString(Path.of("inputs\\sample.txt"));
                //String input = Files.readString(Path.of("inputs\\day" + inp + ".txt"));
                Method solver = Class.forName("solutions.Day" + inp)
                        .getMethod("preprocess", String.class, int.class);
                System.out.println("Star 1 Result: " +
                        solver.invoke(null, input, 1));
                System.out.println("Star 2 Result: " +
                        solver.invoke(null, input, 2));
            } catch (Exception ignore) {
                break;
            }
        }
    }

    static String[] intros = new String[]{
            """
               ,---,                                                           ___                                           ,----..                                                   ,----,       ,----..          ,----,         ,----,  \s
              '  .' \\             ,---,                                      ,--.'|_                        .--.,           /   /   \\                   ,---,                        .'   .' \\     /   /   \\       .'   .' \\      .'   .' \\ \s
             /  ;    '.         ,---.'|                             ,---,    |  | :,'             ,---.   ,--.'  \\         |   :     :    ,---.       ,---.'|                      ,----,'    |   /   .     :    ,----,'    |   ,----,'    |\s
            :  :       \\        |   | :      .---.              ,-+-. /  |   :  : ' :            '   ,'\\  |  | /\\/         .   |  ;. /   '   ,'\\      |   | :                      |    :  .  ;  .   /   ;.  \\   |    :  .  ;   |    :  .  ;\s
            :  |   /\\   \\       |   | |    /.  ./|    ,---.    ,--.'|'   | .;__,'  /            /   /   | :  : :           .   ; /--`   /   /   |     |   | |    ,---.             ;    |.'  /  .   ;   /  ` ;   ;    |.'  /    ;    |.'  / \s
            |  :  ' ;.   :    ,--.__| |  .-' . ' |   /     \\  |   |  ,"' | |  |   |            .   ; ,. : :  | |-,         ;   | ;     .   ; ,. :   ,--.__| |   /     \\            `----'/  ;   ;   |  ; \\ ; |   `----'/  ;     `----'/  ;  \s
            |  |  ;/  \\   \\  /   ,'   | /___/ \\: |  /    /  | |   | /  | | :__,'| :            '   | |: : |  : :/|         |   : |     '   | |: :  /   ,'   |  /    /  |             /  ;  /    |   :  | ; | '     /  ;  /        /  ;  /   \s
            '  :  | \\  \\ ,' .   '  /  | .   \\  ' . .    ' / | |   | |  | |   '  : |__          '   | .; : |  |  .'         .   | '___  '   | .; : .   '  /  | .    ' / |            ;  /  /-,   .   |  ' ' ' :    ;  /  /-,      ;  /  /-,  \s
            |  |  '  '--'   '   ; |:  |  \\   \\   ' '   ;   /| |   | |  |/    |  | '.'|         |   :    | '  : '           '   ; : .'| |   :    | '   ; |:  | '   ;   /|           /  /  /.`|   '   ;  \\; /  |   /  /  /.`|     /  /  /.`|  \s
            |  :  :         |   | '/  '   \\   \\    '   |  / | |   | |--'     ;  :    ;          \\   \\  /  |  | |           '   | '/  :  \\   \\  /  |   | '/  ' '   |  / |         ./__;      :    \\   \\  ',  /  ./__;      :   ./__;      :  \s
            |  | ,'         |   :    :|    \\   \\ | |   :    | |   |/         |  ,   /            `----'   |  : \\           |   :    /    `----'   |   :    :| |   :    |         |   :    .'      ;   :    /   |   :    .'    |   :    .'   \s
            `--''            \\   \\  /       '---"   \\   \\  /  '---'           ---`-'                      |  |,'            \\   \\ .'               \\   \\  /    \\   \\  /          ;   | .'          \\   \\ .'    ;   | .'       ;   | .'      \s
                              `----'                 `----'                                               `--'               `---`                  `----'      `----'           `---'              `---`      `---'          `---'         \s
            """,
            """
             /      \\       /  |                                 /  |                     /      \\        /      \\                 /  |                 /      \\  /      \\  /      \\  /      \\\s
            /$$$$$$  |  ____$$ | __     __   ______   _______   _$$ |_           ______  /$$$$$$  |      /$$$$$$  |  ______    ____$$ |  ______        /$$$$$$  |/$$$$$$  |/$$$$$$  |/$$$$$$  |
            $$ |__$$ | /    $$ |/  \\   /  | /      \\ /       \\ / $$   |         /      \\ $$ |_ $$/       $$ |  $$/  /      \\  /    $$ | /      \\       $$____$$ |$$$  \\$$ |$$____$$ |$$____$$ |
            $$    $$ |/$$$$$$$ |$$  \\ /$$/ /$$$$$$  |$$$$$$$  |$$$$$$/         /$$$$$$  |$$   |          $$ |      /$$$$$$  |/$$$$$$$ |/$$$$$$  |       /    $$/ $$$$  $$ | /    $$/  /    $$/\s
            $$$$$$$$ |$$ |  $$ | $$  /$$/  $$    $$ |$$ |  $$ |  $$ | __       $$ |  $$ |$$$$/           $$ |   __ $$ |  $$ |$$ |  $$ |$$    $$ |      /$$$$$$/  $$ $$ $$ |/$$$$$$/  /$$$$$$/ \s
            $$ |  $$ |$$ \\__$$ |  $$ $$/   $$$$$$$$/ $$ |  $$ |  $$ |/  |      $$ \\__$$ |$$ |            $$ \\__/  |$$ \\__$$ |$$ \\__$$ |$$$$$$$$/       $$ |_____ $$ \\$$$$ |$$ |_____ $$ |_____\s
            $$ |  $$ |$$    $$ |   $$$/    $$       |$$ |  $$ |  $$  $$/       $$    $$/ $$ |            $$    $$/ $$    $$/ $$    $$ |$$       |      $$       |$$   $$$/ $$       |$$       |
            $$/   $$/  $$$$$$$/     $/      $$$$$$$/ $$/   $$/    $$$$/         $$$$$$/  $$/              $$$$$$/   $$$$$$/   $$$$$$$/  $$$$$$$/       $$$$$$$$/  $$$$$$/  $$$$$$$$/ $$$$$$$$/\s
            """,
            """
                 /\\         | |                        | |              / _|    / ____|             | |          |__ \\   / _ \\  |__ \\  |__ \\\s
                /  \\      __| | __   __   ___   _ __   | |_      ___   | |_    | |        ___     __| |   ___       ) | | | | |    ) |    ) |
               / /\\ \\    / _` | \\ \\ / /  / _ \\ | '_ \\  | __|    / _ \\  |  _|   | |       / _ \\   / _` |  / _ \\     / /  | | | |   / /    / /\s
              / ____ \\  | (_| |  \\ V /  |  __/ | | | | | |_    | (_) | | |     | |____  | (_) | | (_| | |  __/    / /_  | |_| |  / /_   / /_\s
             /_/    \\_\\  \\__,_|   \\_/    \\___| |_| |_|  \\__|    \\___/  |_|      \\_____|  \\___/   \\__,_|  \\___|   |____|  \\___/  |____| |____|
            """,
            """
             / _ \\     | |                    | |           / _| /  __ \\           | |       / __  \\|  _  |/ __  \\/ __  \\
            / /_\\ \\  __| |__   __  ___  _ __  | |_    ___  | |_  | /  \\/  ___    __| |  ___  `' / /'| |/' |`' / /'`' / /'
            |  _  | / _` |\\ \\ / / / _ \\| '_ \\ | __|  / _ \\ |  _| | |     / _ \\  / _` | / _ \\   / /  |  /| |  / /    / / \s
            | | | || (_| | \\ V / |  __/| | | || |_  | (_) || |   | \\__/\\| (_) || (_| ||  __/ ./ /___\\ |_/ /./ /___./ /___
            \\_| |_/ \\__,_|  \\_/   \\___||_| |_| \\__|  \\___/ |_|    \\____/ \\___/  \\__,_| \\___| \\_____/ \\___/ \\_____/\\_____/
            """,
            """
              /  _  \\    __| _/___  __  ____    ____  _/  |_    ____  _/ ____\\ \\_   ___ \\   ____    __| _/  ____   \\_____  \\ \\   _  \\  \\_____  \\ \\_____  \\\s
             /  /_\\  \\  / __ | \\  \\/ /_/ __ \\  /    \\ \\   __\\  /  _ \\ \\   __\\  /    \\  \\/  /  _ \\  / __ | _/ __ \\   /  ____/ /  /_\\  \\  /  ____/  /  ____/\s
            /    |    \\/ /_/ |  \\   / \\  ___/ |   |  \\ |  |   (  <_> ) |  |    \\     \\____(  <_> )/ /_/ | \\  ___/  /       \\ \\  \\_/   \\/       \\ /       \\\s
            \\____|__  /\\____ |   \\_/   \\___  >|___|  / |__|    \\____/  |__|     \\______  / \\____/ \\____ |  \\___  > \\_______ \\ \\_____  /\\_______ \\\\_______ \\
                    \\/      \\/             \\/      \\/                                  \\/              \\/      \\/          \\/       \\/         \\/        \\/
            """,
            """
            |   _   ||      | |  | |  ||       ||  |  | ||       |  |       ||       |  |       ||       ||      | |       |  |       ||  _    ||       ||       |
            |  |_|  ||  _    ||  |_|  ||    ___||   |_| ||_     _|  |   _   ||    ___|  |       ||   _   ||  _    ||    ___|  |____   || | |   ||____   ||____   |
            |       || | |   ||       ||   |___ |       |  |   |    |  | |  ||   |___   |       ||  | |  || | |   ||   |___    ____|  || | |   | ____|  | ____|  |
            |       || |_|   ||       ||    ___||  _    |  |   |    |  |_|  ||    ___|  |      _||  |_|  || |_|   ||    ___|  | ______|| |_|   || ______|| ______|
            |   _   ||       | |     | |   |___ | | |   |  |   |    |       ||   |      |     |_ |       ||       ||   |___   | |_____ |       || |_____ | |_____\s
            |__| |__||______|   |___|  |_______||_|  |__|  |___|    |_______||___|      |_______||_______||______| |_______|  |_______||_______||_______||_______|
            """,
            """
               _        _                      _             __     ___             _         ____    ___   ____   ____ \s
              /_\\    __| |__   __  ___  _ __  | |_    ___   / _|   / __\\  ___    __| |  ___  |___ \\  / _ \\ |___ \\ |___ \\\s
             //_\\\\  / _` |\\ \\ / / / _ \\| '_ \\ | __|  / _ \\ | |_   / /    / _ \\  / _` | / _ \\   __) || | | |  __) |  __) |
            /  _  \\| (_| | \\ V / |  __/| | | || |_  | (_) ||  _| / /___ | (_) || (_| ||  __/  / __/ | |_| | / __/  / __/\s
            \\_/ \\_/ \\__,_|  \\_/   \\___||_| |_| \\__|  \\___/ |_|   \\____/  \\___/  \\__,_| \\___| |_____| \\___/ |_____||_____|
            """,
            """
                   d8888      888                            888                    .d888        .d8888b.                888                 .d8888b.   .d8888b.   .d8888b.   .d8888b. \s
                  d88888      888                            888                   d88P"        d88P  Y88b               888                d88P  Y88b d88P  Y88b d88P  Y88b d88P  Y88b\s
                 d88P888      888                            888                   888          888    888               888                       888 888    888        888        888\s
                d88P 888  .d88888 888  888  .d88b.  88888b.  888888        .d88b.  888888       888         .d88b.   .d88888  .d88b.             .d88P 888    888      .d88P      .d88P\s
               d88P  888 d88" 888 888  888 d8P  Y8b 888 "88b 888          d88""88b 888          888        d88""88b d88" 888 d8P  Y8b        .od888P"  888    888  .od888P"   .od888P" \s
              d88P   888 888  888 Y88  88P 88888888 888  888 888          888  888 888          888    888 888  888 888  888 88888888       d88P"      888    888 d88P"      d88P"     \s
             d8888888888 Y88b 888  Y8bd8P  Y8b.     888  888 Y88b.        Y88..88P 888          Y88b  d88P Y88..88P Y88b 888 Y8b.           888"       Y88b  d88P 888"       888"      \s
            d88P     888  "Y88888   Y88P    "Y8888  888  888  "Y888        "Y88P"  888           "Y8888P"   "Y88P"   "Y88888  "Y8888        888888888   "Y8888P"  888888888  888888888
            """,
            """
             /  )    /             _/_         /)    /  )       /          )  /  )     )     )
             /--/  __/ , _ _  ____  /     __   //    /     __ __/  _    .--'  /  /  .--'  .--'\s
            /  (_ (_/_ \\/ </_/ / <_<__   (_)  //_   (__/  (_)(_/_ </_  (__   (__/  (__   (__  \s
                                             />                                               \s
                                            </                                                \s
            """,
            """
                 /\\            /\\\\                                /\\\\                      /\\\\          /\\\\                  /\\\\                                                      \s
                 /\\ \\\\          /\\\\                                /\\\\                    /\\          /\\\\   /\\\\               /\\\\                 /\\ /\\\\      /\\\\      /\\ /\\\\   /\\ /\\\\ \s
                /\\  /\\\\         /\\\\/\\\\     /\\\\   /\\\\    /\\\\ /\\\\  /\\/\\ /\\         /\\\\    /\\/\\ /\\      /\\\\          /\\\\         /\\\\   /\\\\          /\\   /\\\\   /\\\\  /\\\\  /\\   /\\\\ /\\   /\\\\\s
               /\\\\   /\\\\    /\\\\ /\\\\ /\\\\   /\\\\  /\\   /\\\\  /\\\\  /\\\\  /\\\\         /\\\\  /\\\\   /\\\\        /\\\\        /\\\\  /\\\\  /\\\\ /\\\\ /\\   /\\\\           /\\\\  /\\\\     /\\\\     /\\\\      /\\\\ \s
              /\\\\\\\\\\\\ /\\\\  /\\   /\\\\  /\\\\ /\\\\  /\\\\\\\\\\ /\\\\ /\\\\  /\\\\  /\\\\        /\\\\    /\\\\  /\\\\        /\\\\       /\\\\    /\\\\/\\   /\\\\/\\\\\\\\\\ /\\\\         /\\\\   /\\\\      /\\\\   /\\\\      /\\\\  \s
             /\\\\       /\\\\ /\\   /\\\\   /\\/\\\\   /\\         /\\\\  /\\\\  /\\\\         /\\\\  /\\\\   /\\\\         /\\\\   /\\\\ /\\\\  /\\\\ /\\   /\\\\/\\               /\\\\      /\\\\    /\\\\  /\\\\      /\\\\    \s
            /\\\\         /\\\\ /\\\\ /\\\\    /\\\\      /\\\\\\\\   /\\\\\\  /\\\\   /\\\\          /\\\\      /\\\\           /\\\\\\\\     /\\\\     /\\\\ /\\\\  /\\\\\\\\         /\\\\\\\\\\\\\\\\   /\\\\\\     /\\\\\\\\\\\\\\\\/\\\\\\\\\\\\\\\\
            """,
            """
            _______ _________                      _____               ________       _________       _________             ______ _______ ______ ______\s
            ___    |______  /___   _______ _______ __  /_       ______ ___  __/       __  ____/______ ______  /_____        __|__ \\__  __ \\__|__ \\__|__ \\
            __  /| |_  __  / __ | / /_  _ \\__  __ \\_  __/       _  __ \\__  /_         _  /     _  __ \\_  __  / _  _ \\       ____/ /_  / / /____/ /____/ /
            _  ___ |/ /_/ /  __ |/ / /  __/_  / / // /_         / /_/ /_  __/         / /___   / /_/ // /_/ /  /  __/       _  __/ / /_/ / _  __/ _  __/\s
            /_/  |_|\\__,_/   _____/  \\___/ /_/ /_/ \\__/         \\____/ /_/            \\____/   \\____/ \\__,_/   \\___/        /____/ \\____/  /____/ /____/\s
            """
    };
}
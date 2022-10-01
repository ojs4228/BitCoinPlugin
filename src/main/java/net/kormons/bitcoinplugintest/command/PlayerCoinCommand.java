package net.kormons.bitcoinplugintest.command;

import net.kormons.bitcoinplugintest.model.BitCoinManager;
import net.kormons.bitcoinplugintest.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCoinCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length != 0) {

                switch (args[0]) {

                    case "구매":
                        if (args.length == 2) {

                            player.sendMessage("1");

                            try {
                                int count = Integer.parseInt(args[1]);
                            } catch (NumberFormatException e) {
                                player.sendMessage("§c올바른 숫자를 입력해주세요");
                                return true;
                            }
                            int count = Integer.parseInt(args[1]);


                            if (BitCoinManager.buyBitCoin(player, count)) {

                                player.sendMessage(Util.getPrice() * count + "원을 사용하여 비트코인 " + count + " 개를 구매 했습니다.");

                                return true;

                            } else {
                                player.sendMessage("§c돈이 부족합니다.");
                                player.sendMessage("§c현재 비트코인" + count + "개의 가격은" + Util.getPrice() * count + "원 입니다");
                                return true;
                            }


                        } else {
                            player.sendMessage("/비트코인 구매 (수량)");
                            player.sendMessage("2");
                            return true;
                        }
                    case "판매":
                        if (args.length == 2) {

                            try {
                                int count = Integer.parseInt(args[1]);
                            } catch (NumberFormatException e) {
                                player.sendMessage("§c올바른 숫자를 입력해주세요");
                                return true;
                            }
                            int count = Integer.parseInt(args[1]);

                            if (BitCoinManager.sellBitCoin(player, count)) {

                                player.sendMessage("비트코인" + count + "개를 판매하여" + Util.getPrice() * count + "원을 벌었습니다.");

                                return true;

                            } else {

                                player.sendMessage("§c코인이 부족합니다");
                                player.sendMessage("§c현재 소유코인은" + BitCoinManager.getPlayersBitCoin(player) + " 개 입니다");
                                return true;

                            }

                        } else {
                            player.sendMessage("/비트코인 판매 (수량)");
                            return true;
                        }

                    case "정보":

                        BitCoinManager.BitCoinInfo(player);
                        return true;
                    case "설정":

                        if(!player.isOp()){
                            player.sendMessage("§cop전용 명령어 입니다.");
                            return true;
                        }

                        if(args.length == 2){
                            player.sendMessage("/비트코인 설정 (수량) (플레이어)");
                            return true;
                        }

                        if (args.length == 3){

                            try {
                                int count = Integer.parseInt(args[1]);
                            }catch (NumberFormatException e){
                                player.sendMessage("§c올바른 숫자를 입력해주세요");
                                return true;
                            }
                            int count = Integer.parseInt(args[1]);

                            OfflinePlayer offlinePlayer = (OfflinePlayer) player;

                            if(!offlinePlayer.hasPlayedBefore()){
                                player.sendMessage("§c해당 플레이어는 접속한적이 없습니다.");
                                return true;
                            }

                            OfflinePlayer tatgetPlayer= Bukkit.getOfflinePlayer(args[2]);

                            BitCoinManager.setPlayersBitCoin((Player) tatgetPlayer, count);
                            player.sendMessage(tatgetPlayer.getName() + "님의 비트코인을" +count+ "개로 설정 했습니다.");

                        }

                    default:
                        player.sendMessage(" 비트코인 명령어 사용법 ");
                        player.sendMessage("/비트코인 구매 (수량)");
                        player.sendMessage("/비트코인 판매 (수량)");
                        player.sendMessage("/비트코인 정보");
                        player.sendMessage("op 전용:/비트코인 설정 (수량) (플레이어)");
                }   
            }else {

                player.sendMessage(" 비트코인 명령어 사용법 ");
                player.sendMessage("/비트코인 구매 (수량)");
                player.sendMessage("/비트코인 판매 (수량)");
                player.sendMessage("/비트코인 정보");
                player.sendMessage("op 전용:/비트코인 설정 (수량) (플레이어)");

            }
        }
        return false;
    }
}

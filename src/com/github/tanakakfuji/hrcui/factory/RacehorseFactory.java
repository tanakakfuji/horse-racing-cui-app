package com.github.tanakakfuji.hrcui.factory;

import com.github.tanakakfuji.hrcui.horse.CastratedMaleRacrhorse;
import com.github.tanakakfuji.hrcui.horse.FemaleRacehorse;
import com.github.tanakakfuji.hrcui.horse.MaleRacehorse;
import com.github.tanakakfuji.hrcui.horse.Racehorse;

import java.util.Random;

public class RacehorseFactory {
    private static String[][] wordList = {
            {
                    "ゴールデン", "シルバー", "ブルー", "ブラック", "ホーリー", "ダーク", "スカイ", "レッド", "クレイジー", "ファイアー",
                    "ウィンド", "シャイニング", "マイティ", "スウィート", "ディープ", "ハイパー", "サイレント", "ワイルド", "ノーブル", "ピュア",
                    "グレート", "フローズン", "ストーム", "メタリック", "ジェントル", "ライトニング", "エターナル", "フェアリー", "シャドウ", "エレガント",
                    "ゴシック", "トゥルー", "ハード", "ラスト", "ファントム", "ブレイブ", "フラッシュ", "ヘブンリー", "レジェンド", "アメイジング",
                    "ミスティック", "クイック", "ロイヤル", "ファイナル", "シャープ", "ビューティフル", "サンダー", "フライング", "ジャイアント", "マジカル"
            },
            {
                    "シップ", "ハート", "ドリーム", "ウルフ", "スター", "アロー", "クラウン", "ナイト", "フェザー", "ウィング",
                    "サンダー", "ブレード", "ソウル", "スピリット", "プリンス", "クイーン", "グリフォン", "リバティ", "ストライカー", "フォース",
                    "フレイム", "アイズ", "キング", "ライダー", "ドラゴン", "タイガー", "フェニックス", "ジェット", "シャドウ", "メイデン",
                    "キャノン", "フォックス", "パンサー", "レイジ", "ブラッド", "クロス", "オーラ", "ストーム", "ソード", "スノウ",
                    "ロード", "リーパー", "ガーディアン", "ブルーム", "エンジェル", "ブリーズ", "ローズ", "サファイア", "クレスト", "レクイエム"
            }
    };

    public static Racehorse createRacehorse(int id) {
        Random rand = new Random();
        String racehorseName = wordList[0][rand.nextInt(wordList[0].length)] + wordList[1][rand.nextInt(wordList[1].length)];
        Racehorse racehorse;
        int sr = rand.nextInt(3);
        switch (sr) {
            case 0 -> {
                racehorse = new MaleRacehorse(racehorseName, id);
            }
            case 1 -> {
                racehorse = new FemaleRacehorse(racehorseName, id);
            }
            case 2 -> {
                racehorse = new CastratedMaleRacrhorse(racehorseName, id);
            }
            default -> {
                throw new IllegalStateException("予期しない値: " + sr);
            }
        }
        return racehorse;
    }
}

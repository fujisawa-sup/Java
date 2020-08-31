package main.machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import main.database.Product;

public class ProductSelectPhase {

	private Product pro;

	public void setPro(Product pro) {
		this.pro = pro;
	}

	//引数なし（setDBメソッドに引数）のパターン
	public ProductSelectPhase() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	//引数あり（pspに引数）のパターン
	public ProductSelectPhase(Product pro) {
		// TODO 自動生成されたコンストラクター・スタブ
		//		 pro = pro;
		this.setPro(pro);
	}

	private Zaiko zai;

	public void setZai(Zaiko zai) {
		// TODO 自動生成されたメソッド・スタブ
		this.zai = zai;
	}

	//商品情報のリスト
	List<Product> ristP;

	//在庫情報のリスト
	List<Zaiko> ristZ;

	public int main() throws SQLException {

		//商品情報のリスト
		ristP = pro.getData();

		//リスト表示
		showlist();

		//選択された商品番号
		int select = sentaku();

		return select;
	}

	/**
	 * リスト表示
	 * @throws SQLException
	 */
	public void showlist() throws SQLException {

		String urikire;//売切文言

		//在庫情報のリスト取得
		ristZ = zai.getZaiko();

		//商品番号（１～）
		int id = 1;

		//商品情報の数だけループ
		for (Product a : ristP) {

			//一致する在庫情報
			Zaiko kazu = null;
			//hinbanとidが一致するリスト要素を探索
			for (int i1 = 0; i1 < ristZ.size(); i1++) {

				//hinbanとidが一致したら在庫情報を設定
				if (ristZ.get(i1).getHinban() == id) {
					kazu = ristZ.get(i1);
				}

			}
			id++;

			//在庫が0以下なら売切表示を追加
			if (kazu.getNokori() < 1) {
				urikire = "|売切";
			} else {
				urikire = "";
			}
			//リストの表示
			System.out.println("|" + a.getId() + "|" + a.getName() + "|" + a.getPrice() + "円" + urikire);

		}

	}

	/**
	 * 商品選択
	 * @param
	 * @return pnum
	 * @throws SQLException
	 */
	public int sentaku() throws SQLException {

		String input;//入力値
		int pnum = 0;//選択値

		//商品選択入力受付
		do {
			System.out.print("商品を選択してください:");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				input = br.readLine();
				pnum = Integer.valueOf(input);
			} catch (IOException e1) {
			} catch (NumberFormatException e2) {
				System.out.println("※数字で入力してください");
			}
		} while (pnum < 1 || pnum > ristP.size());

		return pnum;
	}

	/**
	 * 選択した商品の在庫数を取得
	 * @param sele
	 * @return danball.get(choice).getNokori()
	 */
	public int choizai(int sele) {

		return zai.senzai(sele);

	}

	/**
	 * DBの在庫数を増やす
	 * @param hinban
	 * @throws SQLException
	 */
	public void fuyasu(int hinban) throws SQLException {
		zai.fuya(hinban);
	}

}

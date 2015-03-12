package com.growthbeat.message;

import java.util.ArrayList;

import com.growthbeat.message.model.Message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

public class BasicMassageHandler {
	Context context;
	
	public BasicMassageHandler(Context context)
	{
		this.context = context;
	}
	
	boolean handleMessage (Message message)
	{
        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < message.getButtons().size(); i++)
        {
            labels.add(message.getButtons().get(i).getLabel());
        }
        final CharSequence[] labelsArray = (CharSequence[])labels.toArray(new CharSequence[0]);

        AlertDialog.Builder listDlg = new AlertDialog.Builder(context);

        TextView text = new TextView(context);
        text.setText(message.getBody());
//                listDlg.setMessage(message.body);
        listDlg.setView(text);

        listDlg.setTitle(message.getTitle());
        listDlg.setItems(
                labelsArray,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // リスト選択時の処理
                        // which は、選択されたアイテムのインデックス
                    }
                });

        // 表示
        listDlg.create().show();
        return true;

	}
	
}

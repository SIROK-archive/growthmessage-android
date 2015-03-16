package com.growthbeat.message;

import java.util.ArrayList;

import com.growthbeat.message.model.GMMessage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

public class BasicMassageHandler implements MessageHandler{
	Context context;
	
	public BasicMassageHandler(Context context)
	{
		this.context = context;
	}

	@Override
	public boolean handleMessage(final GMMessage message, final GrowthMessage manager) 
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
        listDlg.setView(text);

        listDlg.setTitle(message.getTitle());
        listDlg.setItems(
                labelsArray,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	manager.didSelectButton(message.getButtons().get(which), message);
                    }
                });

        // •\Ž¦
        listDlg.create().show();
        return true;

	}
	
}

package com.android.lonoti.customview;

import com.android.lonoti.R;
import com.android.lonoti.adapter.ContactAdapter;
import com.android.lonoti.adapter.data.Contact;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class ContactsMultiAutoCompleteView extends MultiAutoCompleteTextView implements OnItemClickListener{

	public ContactsMultiAutoCompleteView(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}
	
	public ContactsMultiAutoCompleteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ContactsMultiAutoCompleteView(Context context, AttributeSet attrs,
			   int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	 
	private void init(Context context) {
		// TODO Auto-generated method stub
		setOnItemClickListener(this);
		//addTextChangedListener(textWatcher);

	}

	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence charsequence, int start, int before, int count) {
			// TODO Auto-generated method stub
			
			if(count >=1){
				//if(charsequence.charAt(start) == ',')
				//setChips(); // generate chips
			}

		}
		
		

		@Override
		public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable editable) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void setChips(Contact contact) {
		// TODO Auto-generated method stub
		
		SpannableStringBuilder ssb = new SpannableStringBuilder(getText());
		   // split string wich comma
		   String chips[] = getText().toString().trim().split(",");
		   int x =0;
		   // loop will generate ImageSpan for every country name separated by comma
		   for(String c : chips){
		    // inflate chips_edittext layout 
		    LayoutInflater lf = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		    TextView textView = (TextView) lf.inflate(R.layout.item_textview, null);
		    textView.setText(resolveName(c)); // set text
		    //setFlags(textView, c); // set flag image
		    //textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
		    // capture bitmapt of genreated textview
		    int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		    textView.measure(spec, spec);
		    textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
		    Bitmap b = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(),Bitmap.Config.ARGB_8888);
		    Canvas canvas = new Canvas(b);
		    canvas.translate(-textView.getScrollX(), -textView.getScrollY());
		    textView.draw(canvas);
		    textView.setDrawingCacheEnabled(true);
		    Bitmap cacheBmp = textView.getDrawingCache();
		    Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
		    textView.destroyDrawingCache();  // destory drawable
		    // create bitmap drawable for imagespan
		    BitmapDrawable bmpDrawable = new BitmapDrawable(viewBmp);
		    bmpDrawable.setBounds(0, 0,bmpDrawable.getIntrinsicWidth(),bmpDrawable.getIntrinsicHeight());
		    // create and set imagespan 
		    ssb.setSpan(new ImageSpan(bmpDrawable),x ,x + c.length() + 1 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		    ssb.append(" ");
		    x = x+ c.length() +2;
		   }
		   // set chips span 
		   setText(ssb);
		   // move cursor to last 
		   setSelection(getText().length());
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		System.out.println("in Item click " + arg2 + " " + arg3);
		
		Contact contact = ((ContactAdapter)getAdapter()).getFilteredList().get(arg2);
		System.out.println(contact.getName());
		
		
		System.out.println(this.getText());
		//this.setText(this.getText() + "," + contact.getName());
		System.out.println(getText());
		setChips(contact);
	}
	
	String resolveName(String contactString){
		
		if(contactString.indexOf("<") != -1 && contactString.indexOf(">") != -1){
			
			return contactString.substring(contactString.indexOf("<") + 1, contactString.indexOf(">"));
			
		}
		return "";
	}
	
}

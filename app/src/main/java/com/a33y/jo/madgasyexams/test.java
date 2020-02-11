package com.a33y.jo.madgasyexams;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ahmed on 14/3/2018.
 */

public class test {
    /*private final String[] listContent = {"يعني خلاص انسى واروح","احترق يا خفوقي وابردي يا عضامي","الكفو مالي عذر منه","خط المدينه","ابك حنا عتيبه","يا متعب الخيل","من نوانا خلجت امه","لاجل شيخ للكرم", "يا شجون القلب لو تدري" ,"الي ما يعرف فعولنا قله","اي والله اني قدها", "معوض خير ياقلبي","شطر بطر شبله","دامها دعوة تحدي","كامل الاوصاف","مطنوخ يا جعلك ذخر","يا سمو المجد هيا","يا كنترول سجل لنا اقوى دخول","غابو عنا ورحلو" ,"شيخة الحلوات","العن الشيطان","عيدية الوطن","مراره وسكر", "تحزم تحزم (دي جي قرطوش)","ماخذ حقه بدق خشوم (دي جي قرطوش)", "يالغالي على هونك(دي جي قرطوش)","ابسط حقوقي (دي جي قرطوش)","ارض الاصاله","لاتزوج مالك ومال الحريم","شيخة جيلها","اجبرو محبوبي بقوه","احرقي جو العذراء","يا ماخذ قلبي مني","كذا البنات","هزني في الحشا","ولع الشباب","يا دانا دانا","قوات الطورائ","حياتي كلش مابيها",",وينهم ووين اخبارهم (دي جي قرطوش)","شفتها يا سعود صدفه","اهلا اهلا يا هلا","ولع الشباب خل السماء تدوي دوي","انت ما تستاهل احساسي","حرب زلزلة","دق خشم اللاش دقه","سلطنة عتيبه","يا خاطري لاتضيق","ياراس راسي لا مصدع ولا شيء","اليالي علمتني","يا هاجسي هات الجزيل","ايه انا سعودي","سلاطين عنزه","ارفقي بي يابدويه","عنزة متميزة","من يخاوينا نعزه","عنزه سما سما","يا سايق الشاص","الراس شاش","شيلة ياعتيبة علموهم","شيلة ياما عطينا","شيلة لايهمك لايهمك يا وطنا","شيلة هيبة ملوك","شيلة وقتي الي","شيلة يا صاحبي عود","شيلة سرها يا هاجسي","شيلة عشقتي يا عشقتي","قحطان محزمي المليان","شيلة هاي هاي","شيلة يا زين ما ربوني اهلي","شيلة هب البراد","شيلة يالحبيب انا اتمنالك" ,"شيلة سنابها"};


    private final int[] resID = {R.raw.a45,R.raw.a46,R.raw.a47,R.raw.a48,R.raw.a49,R.raw.a50,R.raw.a51,R.raw.a52,R.raw.a44,R.raw.a35,R.raw.a36,R.raw.a37,R.raw.a38,R.raw.a39,R.raw.a40,R.raw.a41,R.raw.a42,R.raw.a43, R.raw.a34, R.raw.a30, R.raw.a31,R.raw.a32,R.raw.a33,R.raw.a29, R.raw.a12, R.raw.a13, R.raw.a14, R.raw.a15, R.raw.a16, R.raw.a17, R.raw.a18, R.raw.a19, R.raw.a21, R.raw.a22, R.raw.a23, R.raw.a24, R.raw.a25, R.raw.a26, R.raw.a27, R.raw.a28,R.raw.a11 ,R.raw.a2,R.raw.a3,R.raw.a5,R.raw.a6,R.raw.a7,R.raw.a8,R.raw.a9,R.raw.a10,R.raw.aaaa, R.raw.dddd, R.raw.gggg, R.raw.hhhh, R.raw.iiii, R.raw.llll, R.raw.nnnn, R.raw.oooo, R.raw.qqqq, R.raw.uuuu, R.raw.aaa, R.raw.ddd, R.raw.mmm,R.raw.pp,R.raw.qq,R.raw.rr,R.raw.term,R.raw.tt,R.raw.uu
            ,R.raw.bb, R.raw.ff, R.raw.ii, R.raw.jj,R.raw.uuu};
    private static final String TAG ="Test";
    List<Song> songs = new ArrayList<>();
    */
   public static void create(Context c){

       FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref =  database.getReference("Bac");
        for(Term t: DataList.getTermss()) {
            myref.push().setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {


                    } else {


                    }
                }

            });
        }

    }
}

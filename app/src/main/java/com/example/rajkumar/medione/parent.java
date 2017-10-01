package com.example.rajkumar.medione;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class parent extends Fragment
{


    public parent() {
        // Required empty public constructor
    }

    TextView a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_parent, container, false);
        a=(TextView)view.findViewById(R.id.a);
        b=(TextView)view.findViewById(R.id.b);
        c=(TextView)view.findViewById(R.id.c);
        d=(TextView)view.findViewById(R.id.d);
        e=(TextView)view.findViewById(R.id.e);
        f=(TextView)view.findViewById(R.id.f);
        g=(TextView)view.findViewById(R.id.g);
        h=(TextView)view.findViewById(R.id.h);
        i=(TextView)view.findViewById(R.id.i);
        j=(TextView)view.findViewById(R.id.j);
        k=(TextView)view.findViewById(R.id.k);
        l=(TextView)view.findViewById(R.id.l);
        m=(TextView)view.findViewById(R.id.m);
        n=(TextView)view.findViewById(R.id.n);
        o=(TextView)view.findViewById(R.id.o);
        p=(TextView)view.findViewById(R.id.p);
        q=(TextView)view.findViewById(R.id.q);
        r=(TextView)view.findViewById(R.id.r);

        String a1="For the purposes of availing the Services and/or transacting with the Third " +
                "Party Service Providers through the Website, You are required to obtain" +
                "registration, in accordance with the procedure established by Medione in this" +
                " regard. As part of the registration process, medione may collect the " +
                "following personal information from You:";

        a.setText(a1);


        String b1="Organizations, companies, and businesses may not become registered members on the" +
                "Website or use the Website through individual members.";
        b.setText(b1);

        String c1="The commercial / contractual terms include without limitation - price, shipping " +
                "costs, payment methods, payment terms, date,period and mode of delivery, warranties" +
                " related to Pharmaceutical Goods and Services offered for sale by the Third Party" +
                " Pharmacies,and after sales services related to such Pharmaceutical Goods and " +
                "Services. Medione does not have any control over, and does not determine or advise" +
                " or in any way involve itself in the offering or acceptance of, such commercial /" +
                " contractual terms offered by and agreed to, between You and the Third Party" +
                " Pharmacies.";
        c.setText(c1);



        String d1="Medione does not make any representation or warranty as to legal title" +
                "of the Pharmaceutical Goods and Services offered for sale by the Third " +
                "Party Pharmacies on the Website. At no time shall any right, title, claim " +
                "or interest in the products sold through or displayed on the Website vest " +
                "with medione nor shall Medione have any obligations or liabilities in " +
                "respect of any transactions on the Website. You agree and acknowledge " +
                "that the ownership of the inventory of such Pharmaceutical Goods and " +
                "Services shall always vest with the Third Party Pharmacies, who are " +
                "advertising or offering them for sale on the Website and are the ultimate sellers";
        d.setText(d1);

        String e1="You agree and acknowledge that the Third Party Pharmacies shall be solely " +
                "responsible for any claim/ liability/ damages that may arise in the event " +
                "it is discovered that such Third Party Pharmacies do not have the sole " +
                "and exclusive legal ownership over the Pharmaceutical Goods and Services " +
                "that have been offered for sale on the Website by such Third Party " +
                "Pharmacies, or did not have the absolute right, title and authority to " +
                "deal in and offer for sale such Pharmaceutical Goods and Services on the " +
                "Website.";
        e.setText(e1);

        String f1="Medione is not responsible for any unsatisfactory, delayed," +
                "non-performance or breach of the contract entered into between You and " +
                "the Third Party Pharmacies for purchase and sale of goods or services " +
                "offered by such Third Party Pharmacies on the Website.";
        f.setText(f1);


        String g1="Medione cannot and does not guarantee that the concerned Third Party " +
                "Pharmacies will perform any transaction concluded on the Website.";
        g.setText(g1);

        String h1="The Third Party Pharmacy(s) are solely responsible for ensuring that the " +
                "Pharmaceutical Goods and Services offered for sale on the Website are " +
                "kept in stock for successful fulfillment of orders received. " +
                "Consequently, Medione is not responsible if the Third Party Pharmacy(s)" +
                "does not satisfy the contract for sale of Pharmaceutical Goods and " +
                "Services which are out of stock, back ordered or otherwise unavailable, " +
                "but were shown as available on the Website at the time of placement of " +
                "order by You.";
        h.setText(h1);

        String i1="You agree and acknowledge that the respective Third Party Pharmacies are " +
                "exhibiting Third Party Content which includes catalogue of " +
                "drugs/ pharmaceutical products or services, and information in relation " +
                "to such drugs/ pharmaceutical products or services, on the Website.";
        i.setText(i1);

        String j1="The Website is a platform that can be used by the Users to purchase " +
                "various drugs and pharmaceutical products that requires a valid medical " +
                "prescription issued by a medical expert/ doctor to be provided to a " +
                "registered pharmacist for the purpose of dispensing such medicine " +
                "(“Prescription Drugs”), offered for sale on the Website by Third Party " +
                "Pharmacies. You are required to make the original prescription available " +
                "at the time of receipt of delivery of Prescription Drugs.";
        j.setText(j1);

        String k1="Medione shall maintain a record of all the prescriptions uploaded by the " +
                "Users.";
        k.setText(k1);


        String l1="For the avoidance of any doubt, it is hereby clarified that any reference " +
                "of the term ‘offer/ offered for sale by the Third Party Pharmacy’, " +
                "as appearing in these Terms of Use, shall be construed solely as an " +
                "‘invitation to offer for sale’ by any such Third Party Pharmacy.";
        l.setText(l1);


        String m1="You agree and acknowledge that the property and title in the " +
                "Pharmaceutical Drugs and Services ordered by You shall stand immediately " +
                "transferred to You upon the dispensation of Pharmaceutical Drugs and " +
                "Services and the raising of the invoice at the concerned Third Party " +
                "Pharmacy. Accordingly, the sale of Pharmaceutical Drugs and Services is " +
                "concluded at the concerned Third Party Pharmacy itself.";
        m.setText(m1);


        String n1="As part of the Services, Medione provides Medione Content on the " +
                "Website targeted at general public for informational purposes only " +
                "and does not constitute professional medical advice, diagnosis, " +
                "treatment or recommendations of any kind. Medione Content is subject " +
                "to the following rules/ information:";
        n.setText(n1);


        String o1="i) Medione Content is original and is relevant to the general public;";
        o.setText(o1);

        String p1="ii) Medione maintains principles of fairness, accuracy, objectivity, " +
                "    and responsible, independent reporting;";
        p.setText(p1);



        String q1="iii) The member of Medione has to fully disclose any potential conflict " +
                "     of interest with any of the Third Party Service Providers";
        q.setText(q1);


        String r1="iv) Medione’s editorial staff holds the responsibility of providing " +
                "   objective, accurate, and balanced accounts of events and issues;";
        r.setText(r1);



        return  view;
    }

}

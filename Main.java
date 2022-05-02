package com.company;
import java.math.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {




    public static boolean paritás (long x)
    {
        //ezt a függvényt a millerrabin algoritmusnak hoztam létre

        if(x%2==0)
        {
            return true;

        }
        else
            return false;


    }


    public static long[] KibovitettEuklidesz(long elso,long masodik)
    {
        long x=1,y=0,xi=0,yi=1,tarolx=0,taroly=0;
        long i = 0;
        long tarolelso=elso;
        long tarolmasodik=masodik;
        long r=1;
        long q=0;
        long [] returns=new long[4];
        //Mivel több helyen is felhasználom az euklideszi algoritmust es több választ is kell visszaadjon ezért tömbbe rendeztem a válaszokat
        ArrayList<Long> a=new ArrayList();//ezt azért deklaráltam mert az ri-értékeit ebben fogom tárolni

        //amig az utolsó szám nagyobb mint 0 az euklideszi algoritmus ri -sorából
        //tehát mindig a két összehasonlítandó számból a második
        while(masodik>0)
        {
            //a két szám osztási maradéka lesz az r
             r=elso%masodik;
             //a q pedig az osztás
             q=elso/masodik;
             //az r+eket pedig egy listába rendezem,hogy az utolsó értéket tudjam lekérni
             a.add(r);


             //folyamatosan cserélgetjük az értékeket tehát a maradékból lesz az uj második szám a második számból pedig lesz az első
            elso=masodik;
            masodik=r;

           tarolx=xi;
           //eltárolom az xi értéket
           xi=xi*q+x;
           //és az uj xi értéket pedig ugy kapjuk,hogy x(i-1)*q(i-1)+x(i-2)
           x=tarolx;


            //ugyanazt megcsinálom az y-al is mint az x-el
           taroly=yi;
           yi=yi*q+y;
           y=taroly;
            i++;

        }
        returns[0]=a.get(a.size()-2);
        //az első megoldás az az utolso nem 0 maradék lesz

        x=HatvanyrEemel(-1,i)*tarolx;
        returns[2]=x;
        //a harmadik megoldás az x lesz amit ugy kapunk meg,hogy (-1)^(i-1) * az utolso xi érték


        y=HatvanyrEemel(-1,i+1)*taroly;
        returns[3]=y;
        //a negyedik megoldás az y lesz amit ugy kapunk meg,hogy (-1)^(i) * az utolso yi érték

        long d=y%tarolelso;
        if(d<0)
        {
            d=tarolelso+d;
        }
        returns[1]=d;
        //a második megoldás pedig a d amit a d*e=ax+by egyenletből kifejezve kapunk


        return returns;

        }






    public static boolean MillerRabin(long szam,long bazis)
    {
        //1-nél nagyobb páratlan n-ekere
        //vizsgáljuk hogy a szam az adott bázisra nézve prim-e vagy összetett
        long d=szam-1;
        long mod=1;

        int s=0;//osztások számát tárolom benne


            //ha a szám 2 akkor lehet prim
            // A miller rabin algoritmusnak egy primszámot kell megadnunk ami lehet 2 is
         if(szam!=2 && paritás(szam)) {
            System.out.println("A vizsgált számnak páratlannak kell lennie");
            return false;
        }
         //egészen addig osztom 2-vel amig azt lehet maradék nélkül
        else {
            while (d % 2 == 0) {
                //d pedig az utolsó osztás eredményét tárolja mindig
                d = d / 2;
                //s az osztások száma
                s++;
            }
            System.out.println("d=" + d + ",S=" + s);
            //Első lépésben azt vizsgáljuk,hogy a^d=1 -el,ha ez teljesűl akkor nem megyünk tovább,mert a szám prim
            System.out.println("Vizsgáljuk az a^d feltételt");
            if (ModHatvany(bazis, d, szam) == 1) {
                return true;
            } else {
                System.out.println("Az a^d nem teljesűlt ezért tovább kell lépnünk az a^(d*2^i) vizsgálására");

                //ha az első feltltel nem teljesült akkor vizsgáljuk a másodikat aminek az eredménye -1 kell legyen,ha teljesűl akkor a szám prim
                for (long i = 0; i < s; i++) {
                    mod = ModHatvany(bazis, HatvanyrEemel(2, i)*d, szam);
                }
                if(mod==szam-1)
                {
                    return true;
                }
                else
                    //Nem teljesűlt egy feltétel sem,tehát a szám biztosan összetett
                System.out.println("A feltételek nem teljesűltek ezért nem relativ primek");
                    return false;

            }



        }


    }
    public static long HatvanyrEemel(long alap,long kitevo)
    {

        //egyszerű hatványraemelés


        long eredmeny=1;

        if(kitevo==0)
        {
            eredmeny=1;
        }
        else if(kitevo==1)
        {
            eredmeny=alap;
        }
        else
        {
            for(int i=0;i<kitevo;i++)
            {
                eredmeny=eredmeny*alap;
            }
        }


        return eredmeny;
    }

    public static long ModHatvany(long x,long y,long mod)
    {
        //gyorshatványozás
        long szam=1;
        while(y>0)
        {
            long a=y%2;//osztasi maradek
            if(a==1)
            {
                //ahol a maradék=1 azokat vesszük figyelembe
                szam=(x*szam)%mod;
            }
            x=(x*x)%mod;
            y=y/2;
            //a kitevőt mindig osztom 2-vel

        }
        return szam;





    }

    public static long Kinaimaradektetel(long p,long q,long c,long d)

    {
       //Az M tulajdonkeppen ugyanaz mint az n
        long M=p*q;
        System.out.println("M="+M);
        //mivel csak két érték van ezért felcseréljük a 2 értéket m1 lesz q m2 lesz p
        long m1=q;
        long m2=p;
        //kiszámolom a c1-et és c2-t a
        long c1=ModHatvany(c,ModHatvany(d,1,p-1),p);
        long c2=ModHatvany(c,ModHatvany(d,1,q-1),q);
        System.out.println("c1="+c1+" c2="+c2);
        //az euklidesz algoritmusbol kiszedem az x-et és az y-t,mert ez szükséges az üzenet visszafejtéséhez
        long eukl[]=KibovitettEuklidesz(p,q);
        long x=eukl[3];
        long y=eukl[2];
        System.out.println("x="+x+" y="+y);
        //itt csak azert emeltem az elsőre mert kellett a modulonak egy argumentum és igy nem kellett megváltoztatnom a függvényt
        long üzenet=ModHatvany((c1*m1*x)+(c2*m2*y),1,M);
        if(üzenet<0)
        {
            //ha a visszafejtett szám negativ akkor sincs baj mert maradék osztályban vagyunk
            System.out.println("Üzenet");
            üzenet=M+üzenet;
            System.out.println(üzenet);
            return M+üzenet;
        }
        else {
            System.out.println("Üzenet");
            System.out.println(üzenet);
            return üzenet;
        }

    }

    public static void RSA(){

        //bekérem a szükséges számokat a p-t és a q-t és eltárolom őket a szükséges változókba
        System.out.println("Ird be az egyik számot:");
        Scanner bep=new Scanner(System.in);
        long p=bep.nextLong();
        System.out.println("Ird be a másik számot:");
        Scanner beq=new Scanner(System.in);
        long q=bep.nextLong();
        long n,fin,e,d;



        if(MillerRabin(q,p) && MillerRabin(p,q))
        {
            //ha átmennek a millerrabin teszten,tehát primek akkor továbblépek,ha nem akkor kiirom,hogy nem megfelelőek a számok
            System.out.println("Ird be a titkosítandó üzenetet:");
            Scanner beolvas=new Scanner(System.in);
            long m=beolvas.nextLong();
            //bekértem a titkositandó üzenetet és eltároltam az m változóban
            System.out.println("Első lépés:");
            //meghatározom az n értéket
            n=p*q;
            System.out.println("n="+n);
            //a fi(n) értéket is meghatározom
            fin=(p-1)*(q-1);
            System.out.println("fi(n)="+fin);
            //bekérem az e értéket és eltárolom(a titkosító kulcs)
            System.out.println("Második lépés(e=?):");
            Scanner bee=new Scanner(System.in);
            e=bee.nextLong();
            //e meghatározása,e relativ prim kell legyen fi n-el azaz a legnagyobb közös osztojuk 1 kell legyen
                long[] euklideszivalasz=KibovitettEuklidesz(fin,e);
               if(euklideszivalasz[0]!=1|| !(0<e)||!(e<fin)) {
                   //az euklideszi algoritmusom első válasza vizsgálja ezt,tehát ha az lnko nem 1 akkor nem relativ primek
                   System.out.println("Nem megfelelő az e");
                   RSA();
               }
               else
            {
                //ha megfelelő az e szám akkor kezdjük meghatározni a d-t a titkos exponenst
                System.out.println("Harmadik lépés,d meghatározása");
                System.out.println("Kulcspár=("+n+","+e+")");
                //kiiratom a kulcspárt

                System.out.println("d="+euklideszivalasz[1]);
                //a d értékét meghatároztam az euklideszi algoritmusban,az volt a második megoldása a függvénynek
                d=euklideszivalasz[1];

                System.out.println("Negyedik lépés,tikosítás:");
                //elkezdem titkosítani
                    //az m-et az e-edikre emelem  az lesz a titkosított üzenetünk
                long c=ModHatvany(m,e,n);

                System.out.println("A titkosított üzenetünk:"+c);

                //a kinai maradéktétellel pedig visszafejtjük a titkositott üzenetünket
                System.out.println("Visszafejtés");
                Kinaimaradektetel(p,q,c,d);
            }


        }
        else
        {
            System.out.println("A választott számok nem felelnek meg a feltételeknek.(Külöböző nagy primszámok kell legyenek)");
            RSA();
        }


    }

    public static void main(String[] args) {
        RSA();
        //System.out.println(MillerRabin(269,241));
        // System.out.println(paritás(46));
       // System.out.println(ModHatvany(2,3,5));
        //KibovitettEuklidesz(402,123);
        //Kinaimaradektetel(1931,2029,2222,17);
        ///import java.util.StringJoiner;




    }
}

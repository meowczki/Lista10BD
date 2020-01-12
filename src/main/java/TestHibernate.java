import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypeTemplate;
import org.hibernate.type.StandardBasicTypes;

import java.util.*;

public class TestHibernate {

    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Wyświetlenie średniego wieku zwycięzcy");
        String sA = "SELECT AVG(wiekAktora) FROM Laureat WHERE plec IN :plecIN";
        Query qA = session.createQuery(sA);
        String plecIN = "M";
        qA.setParameter("plecIN", plecIN);
        List listM = qA.list();
        plecIN = "F";
        qA.setParameter("plecIN", plecIN);
        List listF = qA.list();
        System.out.println(listF.toString());
        System.out.println(listM.toString());

        System.out.println("Wyświetlanie ile nagród dostali razem członkowie rodziny Fonda");
        String sB = "SELECT count(*) as num from laureaci where imieINazwisko like :nazwisko";
        Query qB = session.createSQLQuery(sB).addScalar("num", StandardBasicTypes.INTEGER).setParameter("nazwisko", "%Fonda%");
        List<String> listFondaNagrody = qB.list();
        System.out.println(listFondaNagrody);
        System.out.println("Wyświetl wiek najmłodszego i najstarszego zwycięzcy oraz najmłodszej i najstarszej zwyciężczyni");

        String sC = "select min(wiekAktora) from Laureat where plec IN :plecIN";
        plecIN = "F";
        Query queryC = session.createQuery(sC).setParameter("plecIN", plecIN);
        List listC = queryC.list();
        System.out.println(listC.toString());
        plecIN = "M";
        queryC = session.createQuery(sC).setParameter("plecIN", plecIN);
        listC = queryC.list();
        System.out.println(listC.toString());

        sC = "select max(wiekAktora) from Laureat where plec IN :plecIN";
        plecIN = "F";
        queryC = session.createQuery(sC).setParameter("plecIN", plecIN);
        listC = queryC.list();
        System.out.println(listC.toString());
        plecIN = "M";
        queryC = session.createQuery(sC).setParameter("plecIN", plecIN);
        listC = queryC.list();
        System.out.println(listC.toString());

        System.out.println(" Oblicz, ile procent nagród zdobyły kobiety");
        String hql1 = "SELECT COUNT(id) FROM Laureat WHERE plec IN :plecIN";
        Query qIN = session.createQuery(hql1);
        plecIN = "F";
        qIN.setParameter("plecIN", plecIN);
        List listIN = qIN.list();
        Long result1 = (Long) listIN.get(0);
        String hql2 = "SELECT COUNT(id) FROM Laureat";
        Long result2 = (Long) session.createQuery(hql2).getSingleResult();
        System.out.printf("%d/%d = %.2f%%\n\n", result1, result2, (100.0 * result1 / result2));


        System.out.println("Wyświetl imiona i nazwiska aktorów/aktorek, którzy zdobyli więcej niż jednego Oskara\n" +
                "i uszereguj ich wg malejącej liczby nagród.");
        String sE = "select imieINazwisko from laureaci group by imieINazwisko having count(imieINazwisko)>1 order by count(imieINazwisko) desc ";
        Query qE = session.createSQLQuery(sE);
        List<String> listE = qE.list();
        System.out.println(listE.toString());

        String sF = "SELECT count(id) FROM Laureat";
        System.out.println(") W pierwszych latach Oskary były przyznawane więcej niż jednej osobie w danej kategorii.\n" +
                "Wyznacz, ile było rozdań nagród.");
        showQueryResults(sF);

        System.out.println(" Wybierz trzech aktorów z listy stu najwybitniejszych aktorów wg serwisu IMDb2\n" +
                "i wyświetl\n" +
                "dla nich zestawienie w formie: imię i nazwisko, rok nagrody i film");
        String sG = " from Laureat where imieINazwisko IN :imieINaziwskoIN";
        List<String> imieINaziwskoIN = new ArrayList<>();
        Query qG = session.createQuery(sG);
        imieINaziwskoIN.add("Marlon Brando");
        imieINaziwskoIN.add("Jack Nicholson");
        imieINaziwskoIN.add("Robert de Niro");
        qG.setParameter("imieINaziwskoIN", imieINaziwskoIN);
        List<Laureat> listG = qG.list();
        listG.stream().forEach(x -> System.out.println(x.getImieINazwisko() + " " + x.getRokPrzyznania() + " " + x.getNazwaFilmu()));

        System.out.println(" Wyznacz, ile było różnych laureatów pośród kobiet i mężczyzn (osobno)");
        String sH = "SELECT COUNT (DISTINCT imieINazwisko) FROM Laureat where plec IN :plecIN";
        Query qH = session.createQuery(sH);
        plecIN = "F";
        qH.setParameter("plecIN", plecIN);
        List<String> listH = qH.list();
        System.out.println(listH.toString());

        plecIN = "M";
        qH.setParameter("plecIN", plecIN);
        listH = qH.list();
        System.out.println(listH.toString());


        session.getTransaction().commit();
        session.close();


    }


    public static void showQueryResults(String queryString) {

        Session session = factory.openSession();
        Query query = session.createQuery(queryString);
        List list = query.list();

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

    }
}

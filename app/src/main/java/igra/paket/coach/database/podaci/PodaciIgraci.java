package igra.paket.coach.database.podaci;

public class PodaciIgraci {

    String [] igraci = { "Mbappe", "Neymar", "Mane", "Salah", "Kane", "De Bruyne", "Alexander-Arnold", "Sterling", "Felix", "Haaland",
            "Sancho", "Fernandes", "Gnabry", "Oblak", "Lukaku", "Son", "Kimmich", "Havertz", "Fati", "Davis",
            "Rashford", "van Dijk", "Alisson", "Messi", "de Ligt", "Robertson", "Courtois", "ter Stegen", "Firmino", "Martinez",
            "Valverde", "Oyarzabal", "Milinkovic-Savic", "Silva", "Dybala", "Sane", "Marquinhos", "Werner", "Varane", "Casemiro",
            "de Jong", "Gimenez", "Pogba", "Alaba", "Rodri", "Camavinga", "Foden", "Richarlison", "Jesus", "Upamecano",
            "Goretzka", "Donnarumma", "Pulisic", "Dias", "Fabinho", "Kante", "Laporte", "Niguez", "Griezmann", "Verratti",
            "Koulibaly", "Koke", "Lewandowski", "Ronaldo", "Ederson", "Rice", "Maddison", "Barella", "Coman", "Martial",
            "de Vrij", "Icardi", "Alaba", "Pedri", "Greenwood", "Kounde", "Osimhen", "Torres", "Hakimi", "Aouar",
            "Arthur", "Hernandez", "Carlos", "Chilwell", "Bastoni", "Dembele", "Llorente", "Tielemans", "Partey", "Neves",
            "Skriniar", "Grealish", "Zaha", "Morata", "Alberto", "Jorginho", "Coutinho", "Hazard", "Kroos", "Ruiz",
            "Chiesa", "Mahrez", "Insigne", "Thiago", "Bentancur", "Wirtz", "Ndombele", "Kulusevski", "Rodrygo", "Zaniolo",
            "Mount", "Calvert-Lewin", "Hernandez", "Ndidi", "Ziyech", "Ocampos", "Depay", "Immobile", "Kovacic", "Sabitzer",
            "Tapsoba", "Wan-Bissaka", "Lodi", "Saka", "Junior", "Bennacer", "Pepe", "Sanchez", "Jota", "Merino",
            "Zakaria", "Abraham", "Soyuncu", "Thuram", "Odegaard", "Keita", "Kessie", "Mendy", "van de Beek", "Kimpembe",
            "Correa", "Gomez", "Jimenez", "Traore", "Lenglet", "Zielinski", "Maguire", "Ake", "Belotti", "Ben Yedder",
            "Carvajal", "Walker", "Aubameyang", "Gundogan", "Wijnaldum", "Pjanic", "Reyna", "Diaby", "Lozano", "Olmo",
            "Neuhaus", "Alli", "Zapata", "Pellegrini", "Silva", "Sule", "Romagnoli", "Cancelo", "Brozovic", "Bellingham",
            "James", "Hudson-Odoi", "Bailey", "Soler", "Konate", "Pavard", "Lo Celso", "Asensio", "Angelino", "Locatelli",
            "de Paul", "Telles", "Semedo", "Gaya", "Guerreiro", "Zouma", "Calhanoglu", "Ginter", "Lacazette", "Rodriguez",
            "Eriksen", "Muller", "Szczesny", "Aguero", "Nkunku", "Digne", "Tonali", "Gomez", "Kamara", "Ceballos",
            "Saint-Maximin", "Pereira", "Klostermann", "Elvedi", "Rebic", "Thauvin", "Rabiot", "Dendoncker", "Kostic", "Manolas",
            "Hazard", "Matip", "Leno", "Kramaric", "David", "Cunha", "Neres", "Militao", "Kean", "Demiral",
            "Malen", "Watkins", "Vlasic", "Soucek", "Hermoso", "Simon", "Onana", "Dembele", "Weghorst", "Correa",
            "Brandt", "Bakayoko", "Haller", "Berardi", "Moreno", "Hojbjerg", "Plea", "Veretout", "Carrasco", "Corona",
            "Guimaraes", "Gravenberch", "Gabriel", "Milenkovic", "Barnes", "Leao", "Ikone", "Torreira", "Bowen", "Tierney"};

    public PodaciIgraci() {
    }

    public int brojIgraca() { return igraci.length; }

    public String uzmiIgraca(int broj) {
        return igraci[broj];
    }
}

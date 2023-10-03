package program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import manga.HtmlPage;
import manga.Images;

public class Main {
    public static void main(String[] args) throws IOException {
        Images mangas = new Images();
        HtmlPage htmlPage = new HtmlPage();
        Scanner sc = new Scanner(System.in);
        String linkManga;
        String link;

        System.out.print("Caminho Destino: ");  // exemplo: C:\Users\lucas.ferreira\Documents\Github\download-brmangas-image\
        String path = sc.nextLine();
        System.out.print("Nome do Mangá: ");
        String nameManga = sc.nextLine().replace(" ", "-").toLowerCase();
        System.out.print("Capitulo minimo: ");
        int chapterMin = sc.nextInt();
        System.out.print("Capitulo maximo: ");
        int chapterMax = sc.nextInt();

        try {
            link = htmlPage.request("https://www.brmangas.net/ler/" + nameManga + "-" + chapterMin + "-online/");
        } catch (FileNotFoundException e) {
            System.out.println("Nome invalido");
            return;
        }

            int index = link.indexOf("/uploads/");
            linkManga = link.substring(0, index);

            System.out.println(linkManga);
            File directory = new File(path + nameManga);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Diretório criado com sucesso!");
            } else {
                System.out.println("Diretorio existente.");
            }

        for (int chapter = chapterMin; chapter <= chapterMax; chapter++) {
            File directoryChapter = new File(path + nameManga + "/chapter" + chapter);
            if (!directoryChapter.exists()) {
                directoryChapter.mkdirs();
            }

            boolean chapterCompleted = false;
            int page = 0;
            while (!chapterCompleted) {
                page++;
                String imageUrl = linkManga + "/uploads/" + nameManga.charAt(0) + "/" + nameManga + "/" + chapter + "/" + page + ".jpg";
                // System.out.println(imageUrl);
                String destinationFile = path + nameManga + "/chapter" + chapter + "\\" + page + ".jpg";

                chapterCompleted = mangas.downloadChapter(imageUrl, destinationFile, chapter);
            }
        }
    }
}
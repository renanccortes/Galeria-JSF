/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.galeria.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Renan Cortes
 */
public class ArquivoUtil {

    //passa um id para evitar qualquer consciendencia de gerar uma string igual
    public static String geraStringAleatoria(String idUsuario) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + idUsuario;
    }

    public static byte[] bufferedToByte(BufferedImage buf) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buf, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    public static byte[] inputStreamToByte(InputStream stream) {
        try {
            return IOUtils.toByteArray(stream);
        } catch (IOException ex) {

            return null;

        }
    }

    public static BufferedImage byteToBuffered(byte[] imageInByte) {

        try {
            if (imageInByte == null) {
                return null;
            }

            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            return bImageFromConvert;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    public static void gravaArquivo(String caminho, byte[] byteArray) {
        java.io.File file = new java.io.File(caminho);
        FileOutputStream in;
        try {
            in = new FileOutputStream(file);
            in.write(byteArray);
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String geraStringAleatoria() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static File byteToFile(String caminho, String nomeArquivo, byte[] arquivo) {

        File file = new File(caminho, nomeArquivo); //Criamos um nome para o arquivo

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file)); //Criamos o arquivo
            bos.write(arquivo); //Gravamos os bytes lá
            bos.close(); //Fechamos o stream.
        } catch (FileNotFoundException ex) {

            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return file;
    }

    public static byte[] fileToByte(File file) {
        FileInputStream fileInputStream = null;

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            return bFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//      
// public static StreamedContent toZip(String pasta, Raiox raiox)  {
//       
//              
//       if(raiox.getImagens()== null || raiox.getImagens().isEmpty()){
//           return null;
//       }
//    
//    
//          String  nomeArquivo = raiox.getIdRaiox().toString()+"_JPG.zip";
//          
//       
//            File dir = new File(pasta);                    
//            dir.mkdirs();
//            File zipFile = new File(dir, nomeArquivo);
//            FileOutputStream fOutputStream;
//            
//           
//       try {
//           fOutputStream = new FileOutputStream(zipFile);
//           ZipOutputStream out = new ZipOutputStream(fOutputStream);                        
//            int cont = 0; 
//            
//             
//                for(ImagemRaiox imagem : raiox.getImagens()){ 
//                    cont++;
//                
//                     if(imagem.getImagem(cont)!=null)
//                        adicionarArquivoParaPastaZip(out, imagem.getImagem(cont),  pasta+ "/IMG_" + cont +".jpg");
//
//                    }
//            out.close();
//            
//             InputStream stream = new FileInputStream(zipFile); 
//          return  new DefaultStreamedContent(stream, "application/zip", zipFile.getName());
//           
//           
//       } catch (FileNotFoundException ex) {
//
//           return null;
//       } catch (IOException ex) {
//
//           return null;
//       }
//       
// }
// 
//      private static void adicionarArquivoParaPastaZip(ZipOutputStream pastaZip, byte[] arquivo, String pathName) throws FileNotFoundException, IOException{
//        byte[] buffer = new byte[1024];     //on the former version there was just one buffer for all files atached to zip. Watch this for possible problems
//        File file = new File(pathName);
//        FileOutputStream xmlWriter = new FileOutputStream(file);
//        xmlWriter.write(arquivo);
//                
//        FileInputStream input = new FileInputStream(file);
//        pastaZip.putNextEntry(new ZipEntry(file.getName()));
//        int tam;
//        while((tam = input.read(buffer)) > 0){
//            pastaZip.write(buffer, 0, tam);
//        }
//        pastaZip.closeEntry();
//        input.close();
//    }

    public static boolean realizaBackup(String caminho) throws IOException, InterruptedException {
        final List<String> comandos = new ArrayList<String>();

        //comandos.add("C:\\Program Files (x86)\\PostgreSQL\\8.4\\bin\\pg_dump.exe"); 
        //comandos.add("C:\\Program Files\\PostgresPlus\\8.4SS\\bin\\pg_dump.exe"); 
        comandos.add("C:\\Program Files\\PostgreSQL\\9.5\\bin\\pg_dump.exe");    // esse é meu caminho  

//           comandos.add("-i");      
        comandos.add("-h");
        comandos.add("localhost");     //ou  comandos.add("192.168.0.1"); 
        comandos.add("-p");
        comandos.add("5432");
        comandos.add("-U");
        comandos.add("postgres");
        comandos.add("-F");
        comandos.add("c");
        comandos.add("-b");
        comandos.add("-v");
        comandos.add("-f");

        comandos.add(caminho);   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  
        comandos.add("nomeBanco");
        ProcessBuilder pb = new ProcessBuilder(comandos);

        pb.environment().put("PGPASSWORD", "");      //Somente coloque sua senha         

        try {
            final Process process = pb.start();

            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();

            process.waitFor();
            process.destroy();
//            AwsService aws = new AwsAmazon();
//            aws.conectar();

//            aws.enviarArquivo(caminho.substring(caminho.lastIndexOf("/") + 1), "backup-banco-usual", new File(caminho));
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
//        catch (AwsExcpetions ex) {
//            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return false;

    }

//    public static StreamedContent ziparArquivo(String pasta, Arquivo arquivo) {
//        String nomeArquivo = arquivo.getNome().concat(".zip");
////        System.out.println("Pasta: "+ pasta);
//        pasta = FacesUtil.getExternalContext().getRealPath(pasta);
////        FacesContext facesContext = FacesContext.getCurrentInstance();
////        ServletContext sContext = (ServletContext) facesContext.getExternalContext().getContext();
////        pasta = sContext.getRealPath(pasta);
//        System.out.println(pasta);
//        File zipFile = null;
//        File dir = null;
//
//        dir = new File(pasta);
//        dir.mkdirs();
//         
//        zipFile = new File(dir, nomeArquivo);
//
//        FileOutputStream fOutputStream;
//
//        try {
//            fOutputStream = new FileOutputStream(zipFile);
//            ZipOutputStream out = new ZipOutputStream(fOutputStream);
//            out.setLevel(Deflater.BEST_COMPRESSION);
//
//            for (ConteudoArquivo conteudo : arquivo.getListaConteudoDoArquivo()) {
//
//                if (conteudo.getConteudo() != null) {
//                    adicionarArquivoParaPastaZip(out, conteudo.getConteudo(), pasta + "/" + conteudo.getNomeArquivo());
//                }
//
//            }
//            out.close();
//            InputStream stream = new FileInputStream(zipFile);
//            return new DefaultStreamedContent(stream, "application/pdf", zipFile.getName());
//
//        } catch (FileNotFoundException ex) {
//
//            return null;
//        } catch (IOException ex) {
//
//            return null;
//        }
//
//    }
    private static void adicionarArquivoParaPastaZip(ZipOutputStream pastaZip, byte[] arquivo, String pathName) throws FileNotFoundException, IOException {
        byte[] buffer = new byte[1024];     //on the former version there was just one buffer for all files atached to zip. Watch this for possible problems
        File file = new File(pathName);

        FileOutputStream xmlWriter = new FileOutputStream(file);
        xmlWriter.write(arquivo);
        FileInputStream input = new FileInputStream(file);
        pastaZip.putNextEntry(new ZipEntry(file.getName()));
        int tam;
        while ((tam = input.read(buffer)) > 0) {
            pastaZip.write(buffer, 0, tam);
        }
        pastaZip.closeEntry();
        input.close();
    }

    public static String formatFileSize(double size) {
        String hrSize = null;

        size = size * 1024.0;

        double k = size;
        double m = size / 1024.0;
        double g = ((size / 1024.0) / 1024.0);
        double t = (((size / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else {
            hrSize = dec.format(k).concat(" KB");
        }

        return hrSize;
    }

}

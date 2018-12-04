//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.security.InvalidKeyException;
//
//import org.xmlpull.v1.XmlPullParserException;
//
//import io.minio.MinioClient;
//import io.minio.errors.MinioException;
//
//public class FileUploader {
//    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException {
//        try {
//            // Create a minioClient with the Minio Server name, Port, Access key and Secret key.
//            MinioClient minioClient = new MinioClient("http://localhost:9000", "ACCESSKEY", "SECRETKEY");
//
//            // Check if the bucket already exists.
//            boolean isExist = minioClient.bucketExists("plotly");
//            if(isExist) {
//                System.out.println("Bucket already exists.");
//            } else {
//                // Make a new bucket called asiatrip to hold a zip file of photos.
//                minioClient.makeBucket("asiatrip");
//            }
//        } catch(MinioException e) {
//            System.out.println("Error occurred: " + e);
//        }
//    }
//}
package ar.scacchipa.twittercloneapp.ui

import android.graphics.Matrix
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.PathParser
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme

@Composable
@Preview(showBackground = true)
fun TwitterLogo() {
    Canvas(
        modifier = Modifier
            .width(250.36363220214844.dp)
            .height(204.dp)
    ) {
        val fillPath = PathParser.createPathFromPathData("M 78.75455395968069 203.99992925470528 C 50.83295624943195 204.02669306675145 23.49351492758635 196.0008018883792 0 180.88038461858577 C 4.069560281344265 181.3535776490515 8.163256314445082 181.58783799404574 12.260116264991465 181.58196570656517 C 35.392662175306725 181.61898512079975 57.86590837616333 173.86126086928627 76.06808102487574 159.55557736483487 C 65.34593317480984 159.35060993785208 54.955678026358434 155.79331550814888 46.349849103906315 149.38098907470703 C 37.7440201814542 142.96866264126518 31.35279391475216 134.02183185924184 28.069641080202324 123.79124866832387 C 31.251163049913888 124.40815917605704 34.484154155138086 124.71958503266005 37.72468902998662 124.72126631303269 C 42.30029288336492 124.72196606801963 46.85566993880513 124.1129388538274 51.271055323016 122.91020445390183 C 39.64130173162327 120.55641426281497 29.18220643825134 114.24345650456169 21.668133808982223 105.04231296886098 C 14.154061179713104 95.84116943316027 10.047742314165454 84.31846267526801 10.045806262623755 72.42899738658559 C 10.045806262623758 72.20057561451739 10.045806262623755 71.9884691306136 10.045806262623755 71.77636319940741 C 17.17702360212166 75.75430010123688 25.154755358462054 77.96410396221009 33.31234707514321 78.2211180600253 C 22.43425559554352 70.93922252004796 14.73614267759063 59.77494016560642 11.786590380257824 47.003120769153945 C 8.837038082925018 34.23130137270147 10.858005895899929 20.813045740127563 17.437695690226573 9.482482650063256 C 30.326903424322964 25.38889340920882 46.40928067747984 38.40211454304782 64.64136310983871 47.67781422354959 C 82.87344554219757 56.953513904051356 102.84789170194847 62.28445360064507 123.2687130292357 63.32475445487283 C 120.66887378607893 52.27003279599277 121.78798474187585 40.66418207775463 126.45192404283121 30.312817876989193 C 131.11586334378657 19.961453676223755 139.06310498399418 11.445024002682079 149.0572384420375 6.088503111492504 C 159.0513719000808 0.7319822203029291 170.53196898854145 -1.16426643864675 181.7129122267458 0.6947721987962723 C 192.89385546495015 2.5538108362392946 203.1481879019476 8.063891367478803 210.88052468471062 16.36776625026356 C 222.36308274427736 14.080266334793784 233.37426712358828 9.854137805375187 243.44391123395894 3.8698325644839895 C 239.60755341700363 15.79293834621256 231.5831399918138 25.915488774126228 220.86118226972437 32.357290571386166 C 231.04553434664015 31.123780033805154 240.9892020916745 28.37419285557487 250.36363220214847 24.19936986403032 C 243.4629113195605 34.541146971962675 234.77946099262368 43.56878103993156 224.71996662108967 50.85945562882857 C 224.83393848941847 53.062094214287676 224.88276922791397 55.28104788064957 224.88276922791397 57.5163181478327 C 224.88276922791397 125.53705683621493 173.18839617608887 203.99992925470528 78.68943291695098 203.99992925470528 ")
        val rectF = RectF()
        fillPath.computeBounds(rectF, true)
        val matrix = Matrix()
        val scale = minOf( size.width / rectF.width(), size.height / rectF.height() )
        matrix.setScale(scale, scale)
        fillPath.transform(matrix)
        val composePathFill = fillPath.asComposePath()

        drawPath(
            path = composePathFill,
            color = Color(red = 0.2980392277240753f, green = 0.6196078658103943f, blue = 0.9215686321258545f, alpha = 1f),
            style = Fill
        )
        drawPath(
            path = composePathFill,
            color = Color.Transparent,
            style = Stroke(
                width = 11.272727012634277f, miter = 4f,
                join = StrokeJoin.Round)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    TwitterCloneAppTheme {
        TwitterLogo()
    }
}
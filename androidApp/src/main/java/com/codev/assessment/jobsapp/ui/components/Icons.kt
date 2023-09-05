package com.codev.assessment.jobsapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun rememberMoreVert(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "more_vert",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 33.625f)
                quadToRelative(-1f, 0f, -1.688f, -0.708f)
                quadToRelative(-0.687f, -0.709f, -0.687f, -1.709f)
                quadToRelative(0f, -0.958f, 0.687f, -1.666f)
                quadToRelative(0.688f, -0.709f, 1.688f, -0.709f)
                reflectiveQuadToRelative(1.688f, 0.709f)
                quadToRelative(0.687f, 0.708f, 0.687f, 1.666f)
                quadToRelative(0f, 1f, -0.687f, 1.709f)
                quadToRelative(-0.688f, 0.708f, -1.688f, 0.708f)
                close()
                moveToRelative(0f, -11.25f)
                quadToRelative(-1f, 0f, -1.688f, -0.687f)
                quadTo(17.625f, 21f, 17.625f, 20f)
                reflectiveQuadToRelative(0.687f, -1.688f)
                quadTo(19f, 17.625f, 20f, 17.625f)
                reflectiveQuadToRelative(1.688f, 0.687f)
                quadToRelative(0.687f, 0.688f, 0.687f, 1.688f)
                reflectiveQuadToRelative(-0.687f, 1.688f)
                quadToRelative(-0.688f, 0.687f, -1.688f, 0.687f)
                close()
                moveToRelative(0f, -11.208f)
                quadToRelative(-1f, 0f, -1.688f, -0.709f)
                quadToRelative(-0.687f, -0.708f, -0.687f, -1.666f)
                quadToRelative(0f, -1f, 0.687f, -1.709f)
                quadTo(19f, 6.375f, 20f, 6.375f)
                reflectiveQuadToRelative(1.688f, 0.708f)
                quadToRelative(0.687f, 0.709f, 0.687f, 1.709f)
                quadToRelative(0f, 0.958f, -0.687f, 1.666f)
                quadToRelative(-0.688f, 0.709f, -1.688f, 0.709f)
                close()
            }
        }.build()
    }
}
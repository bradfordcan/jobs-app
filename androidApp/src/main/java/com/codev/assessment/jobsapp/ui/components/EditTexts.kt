package com.codev.assessment.jobsapp.ui.components

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CEditTextLabel(
    text: String,
    onClickLabelInfo: (() -> Unit)? = null,
    isRequired: Boolean = false,
    paddingBottom: Dp = 8.dp,
) = Row(
    modifier = Modifier
        .padding(bottom = paddingBottom),
    verticalAlignment = Alignment.CenterVertically
) {
    if (onClickLabelInfo != null) {
        Icon(
            Icons.Rounded.Info,
            contentDescription = "Info", tint = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    onClickLabelInfo()
                }
        )
    }

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Black
            )
        ) {
            append("$text ")

        }
        //add text with your different color/style
        if (isRequired) {
            withStyle(
                style = SpanStyle(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("*")
            }
        }
    }

    Text(
        text = annotatedText,
        color = Color.Black,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterVertically)
            .padding(start = 4.dp),
        textAlign = TextAlign.Start
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTextSingleLineBordered(
    focusManager: FocusManager,
    value: String,
    onValueUpdate: (String) -> Unit,
    hint: String,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(8.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
) = Box(
    contentAlignment = Alignment.BottomStart,
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(shape)
        .background(Color.White)
        .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
) {
    BasicTextField(
        modifier = Modifier.onPreviewKeyEvent {
            if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                focusManager.moveFocus(FocusDirection.Down)
                true
            } else {
                false
            }
        },
        keyboardOptions = keyboardOptions,
        value = value,
        singleLine = true,
        onValueChange = {
            onValueUpdate(it)
        },
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp
                    ), // inner padding
            ) {
                if (value.isEmpty()) {
                    CEditTextHintSmall(text = hint)
                }
                innerTextField()
            }
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Start
        )
    )
}

@Composable
fun CEditTextHintSmall(text: String) = Text(
    text = text,
    color = Color.LightGray,
    fontSize = 18.sp,
    maxLines = 1,
    modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight(),
    textAlign = TextAlign.Start
)

@Composable
fun CEditTextSection(text: String) = Text(
    text = text,
    color = Color.Black,
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.wrapContentWidth(),
    textAlign = TextAlign.Start
)
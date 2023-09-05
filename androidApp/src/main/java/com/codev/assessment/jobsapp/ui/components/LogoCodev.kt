package com.codev.assessment.jobsapp.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codev.assessment.jobsapp.android.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    TopAppBarRow {
        LogoCoDev(onSearchJob = {

        }, onChangeUserType = {

        })
    }
}

enum class SearchMode {
    InitialResults, Suggestions, Results, NoResults
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    isSearching: Boolean,
    suggestions: List<String>,
    searchResults: List<Job>,
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var isSearching by mutableStateOf(isSearching)
    var suggestions by mutableStateOf(suggestions)
    var searchResults by mutableStateOf(searchResults)

    val searchDisplay: SearchMode
        get() = when {
            !focused && query.text.isEmpty() -> SearchMode.InitialResults
            focused && query.text.isEmpty() -> SearchMode.Suggestions
            searchResults.isEmpty() -> SearchMode.NoResults
            else -> SearchMode.Results
        }

    override fun toString(): String {
        return "🚀 State query: $query, focused: $focused, searching: $isSearching " +
                "suggestions: ${suggestions.size}, " +
                "searchResults: ${searchResults.size}, " +
                " searchDisplay: $searchDisplay"
    }
}

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    suggestions: List<String> = arrayListOf("android"),
    searchResults: List<Job> = emptyList(),
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            isSearching = searching,
            suggestions = suggestions,
            searchResults = searchResults
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LogoCoDev(onSearchJob: (String) -> Unit, onChangeUserType: () -> Unit) {
    val state: SearchState = rememberSearchState()
    LaunchedEffect(state.query.text) {
        delay(100)
        if (state.query.text.isNotEmpty()) {
            onSearchJob(state.query.text)
        } else {
            onSearchJob("")
        }
    }

    if (state.isSearching) {
        TopAppBarRow {
            SearchBar(
                onSearchJob = {
                    state.isSearching = false
                },
                query = state.query,
                onQueryChange = {
                    state.query = it
                },
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                onBack = {
                    state.isSearching = false
                    state.query = TextFieldValue("")
                },
                searching = true,
                focused = state.focused,
                modifier = Modifier.fillMaxWidth()
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.logocodev2),
                contentDescription = null,
                modifier = Modifier
                    .width(110.dp)
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Article",
                    modifier = Modifier
                        .size(42.dp)
                        .padding(end = 8.dp)
                        .clickable {
                            state.isSearching = true
                        },
                    tint = Color.LightGray
                )
                IconButton(onClick = { }) {
                    Icon(
                        modifier = Modifier.size(36.dp).clickable {
                            onChangeUserType()
                        },
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "",
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBarRow(content: (@Composable () -> Unit)) = Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .background(color = Color.White)
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(8.dp)
) {
    content()
}
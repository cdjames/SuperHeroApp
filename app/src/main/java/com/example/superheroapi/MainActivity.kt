package com.example.superheroapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.superheroapi.model.Image
import com.example.superheroapi.model.SuperHero
import com.example.superheroapi.model.SuperHeroSearch
import com.example.superheroapi.ui.theme.SuperHeroAPITheme
import com.example.superheroapi.ui.viewmodels.SuperHeroSearchUiState
import com.example.superheroapi.ui.viewmodels.SuperHeroUiState
import com.example.superheroapi.ui.viewmodels.SuperHeroViewModel

val hero = SuperHero("hi", "1234", "Batman", image = Image("http://hero.com"))
val search = SuperHeroSearch("hi", hero.name, listOf(hero))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SuperHeroAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SuperHeroVM()
                }
            }
        }
    }
}

@Composable
fun SuperHero(hero: SuperHero,
              searchMethod: (name: String) -> SuperHeroSearch,
              getMethod: (id: String) -> SuperHero,
              modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { SearchField(modifier.padding(bottom = 8.dp)) },
        bottomBar = {},
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        SuperHeroCard(
            hero = hero,
            modifier = modifier
                .padding(it)
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun SuperHeroVM(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    superHeroViewModel: SuperHeroViewModel = viewModel(factory = SuperHeroViewModel.Factory)
) {
    when (val state = superHeroViewModel.superHeroUiState) {
        is SuperHeroUiState.Success -> SuperHero(state.hero, modifier)
        is SuperHeroUiState.Loading -> Text(text = "Loading")
        is SuperHeroUiState.Error -> Text(text = "Error")
    }
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    searchMethod: (name: String) -> SuperHeroSearch,
    getMethod: (id: String) -> SuperHero
) {
    Row(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Search") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(bottom = 8.dp),
            enabled = true,
            singleLine = true
        )
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp),
            onClick = { searchMethod() },
            shape = MaterialTheme.shapes.small,
//            contentPadding = PaddingValues(start = 0.dp, end = 0.dp)
        ) {
            Text("GO")
        }
    }
}

@Composable
fun SuperHeroCard(hero: SuperHero, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = hero.name)
            Text(text = hero.image.url) // TODO replace
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuperHeroPreview() {
    SuperHeroAPITheme {
        SuperHero(hero)
    }
}
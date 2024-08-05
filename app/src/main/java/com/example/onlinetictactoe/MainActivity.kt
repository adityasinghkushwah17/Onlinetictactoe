package com.example.onlinetictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onlinetictactoe.Presentation.TicTacToeField
import com.example.onlinetictactoe.Presentation.TicTacToeViewModel
import com.example.onlinetictactoe.ui.theme.OnlineTicTacToeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnlineTicTacToeTheme {
                val viewModel = hiltViewModel<TicTacToeViewModel>()
                val state by viewModel.state.collectAsState()
                val isConnecting by viewModel.isConnecting.collectAsState()
                val showConnectionerror by viewModel.showConnectionError.collectAsState()
                if (showConnectionerror) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Couldn't Connect to The Server",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    return@OnlineTicTacToeTheme
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .align(Alignment.TopCenter)
                    )
                    {
                        if (!state.connectedPlayers.contains('X')) {
                            Text(text = "Waiting for X", fontSize = 24.sp)
                        }
                        if (!state.connectedPlayers.contains('O')) {
                            Text(text = "Waiting for O", fontSize = 24.sp)


                        }

                    }
                    if (state.connectedPlayers.size == 2 && state.winningPlayer == null && !state.isBoardFull) {
                        Text(
                            text = if (state.playerAtTurn == 'X') "X's Turn" else "O's Turn",
                            fontSize = 32.sp,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                    TicTacToeField(
                        state = state,
                        onAction = viewModel::finishturn,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(16.dp)
                    )
                    if (state.isBoardFull || state.winningPlayer != null) {
                        Text(
                            text = when (state.winningPlayer) {
                                'X' -> "Player X Won"
                                'O' -> "Player O Won"
                                else -> "It's a Draw"
                            },
                            fontSize = 32.sp,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 32.dp)
                        )
                    }
                    if (isConnecting) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                }
            }
        }
    }
}


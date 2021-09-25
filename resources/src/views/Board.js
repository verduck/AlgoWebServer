import React from 'react';
import Container from '@mui/material/Container';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';

class Board extends React.Component {
  render() {
    return (
      <Container maxWidth={false}>
        <Fab color="primary" aria-label="add" sx={{ position: 'absolute', bottom: 16, right: 16 }} href="/write">
          <AddIcon />
        </Fab>
        Hello Board!
      </Container>
    )
  }
}

export default Board;
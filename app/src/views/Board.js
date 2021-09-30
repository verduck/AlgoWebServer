import * as React from 'react'
import { Container, Fab } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

export default class Board extends React.Component {
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
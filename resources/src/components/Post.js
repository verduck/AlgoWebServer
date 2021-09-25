import * as React from 'react'
import Card from '@mui/material/Card'
import CardHeader from '@mui/material/CardHeader'
import { red } from '@mui/material/colors';

function Post() {
  return (
    <Card>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
            이름
          </Avatar>
        }
        title="제목"
        subheader="작성자"
      />
    </Card>
  )
}

export default Post;
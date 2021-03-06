@extends('layouts.app')

@section('content')
    <br><br>
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">Events</div>
                    <div class="panel-body">

                        <a href="{{ url('/moderator/event/create') }}" class="btn btn-primary color-pubuzz" title="Add New Event"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add event</a>
                        <br/>
                        <br/>
                        <div class="table-responsive">
                            <table class="table table-borderless">
                                <thead>
                                    <tr>
                                        <th> Status </th><th> Name </th><th> Location </th><th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                @foreach($event as $item)
                                    <tr>
                                        <td>{{ \App\Enum\EventStatusEnum::getStatusCaption($item->status) }}</td>
                                        <td>{{ $item->name }}</td>
                                        <td>{{ $item->location }}</td>
                                        <td>
                                            <a href="{{ url('/moderator/event/' . $item->id) }}" class="btn color-pubuzz btn-xs" title="View Event"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"/></a>
                                            <a href="{{ url('/moderator/event/' . $item->id . '/edit') }}" class="btn color-pubuzz btn-xs" title="Edit Event"><span class="glyphicon glyphicon-pencil" aria-hidden="true"/></a>
                                            {!! Form::open([
                                                'method'=>'DELETE',
                                                'url' => ['/moderator/event', $item->id],
                                                'style' => 'display:inline'
                                            ]) !!}
                                                {!! Form::button('<span class="glyphicon glyphicon-trash" aria-hidden="true" title="Delete Event" />', array(
                                                        'type' => 'submit',
                                                        'class' => 'btn color-pubuzz btn-xs',
                                                        'title' => 'Delete Event',
                                                        'onclick'=>'return confirm("Confirm delete?")'
                                                )) !!}
                                            {!! Form::close() !!}
                                        </td>
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                            <div class="pagination-wrapper"> {!! $event->render() !!} </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection